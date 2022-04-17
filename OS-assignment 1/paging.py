from collections import deque
from heapq import heapify, heappop, heappush
from typing import Tuple

# Utils ---------

currentTimeStamp = 0


def getCurrentTimestamp() -> int:
    global currentTimeStamp
    currentTimeStamp += 1
    return currentTimeStamp


def getPageLRUPageTableEntryIndex(pageNumber: int, pageTable: list[Tuple[float, int]]) -> int:
    for index, (_, entryPageNumber) in enumerate(pageTable):
        if entryPageNumber == pageNumber:
            return index

    return -1


def lruPageTableEntryExist(pageNumber: int, pageTable: list[Tuple[float, int]]) -> bool:
    return getPageLRUPageTableEntryIndex(pageNumber, pageTable) != -1


def countStepsToNextTouchForPage(pageNumber: int, inReferenceString: list[int], cursor: int) -> int:
    if cursor >= len(inReferenceString):
        return 0

    forwardCollection = inReferenceString[cursor:]
    for subIndex, nextPageNumber in enumerate(forwardCollection):
        if nextPageNumber == pageNumber:
            return subIndex

    return len(forwardCollection)


def getCandidateIndexForPageWithHighestStepsToNextCount(candidatePageNumbers: list[int], inReferenceString: list[int], cursor: int) -> int:
    selectedPageIndex = 0
    selectedPageStepsToNextTouchCount = countStepsToNextTouchForPage(
        candidatePageNumbers[0], inReferenceString, cursor)

    for iii, candidatePageNumber in enumerate(candidatePageNumbers):
        stepsCount = countStepsToNextTouchForPage(
            candidatePageNumber, inReferenceString, cursor)
        if stepsCount > selectedPageStepsToNextTouchCount:
            selectedPageIndex = iii
            selectedPageStepsToNextTouchCount = stepsCount

    return selectedPageIndex

# Actual algorithms


def fifo(frameCount: int, pageReferences: list[int]) -> int:
    """Runs the FIFO algorithm on the given test string and returns the number of page faults"""
    pageTable = deque([], maxlen=frameCount)
    pageFaults = 0
    for pageNumber in pageReferences:
        if pageNumber in pageTable:
            continue
        else:
            pageFaults += 1
            pageTable.append(pageNumber)

    return pageFaults


def lru(frameCount: int, pageReferences: list[int]) -> int:
    """Runs the LRU algorithm on the given test string and returns the number of page faults"""
    pageTable = list[Tuple[float, int]]()
    pageFaults = 0
    for pageNumber in pageReferences:
        if lruPageTableEntryExist(pageNumber, pageTable):
            # Touch the entry
            entryIndex = getPageLRUPageTableEntryIndex(pageNumber, pageTable)
            pageTable[entryIndex] = (getCurrentTimestamp(), pageNumber)
            heapify(pageTable)
        else:
            pageFaults += 1

            if len(pageTable) == frameCount:
                heappop(pageTable)

            heappush(pageTable, (getCurrentTimestamp(), pageNumber))

    return pageFaults


def opt(frameCount: int, pageReferences: list[int]) -> int:
    """Runs the OPT algorithm on the given test string and returns the number of page faults"""
    pageTable = list[int]()
    pageFaults = 0
    for cursor, pageNumber in enumerate(pageReferences):
        if pageNumber in pageTable:
            continue
        else:
            pageFaults += 1

            if len(pageTable) == frameCount:
                indexToReplace = getCandidateIndexForPageWithHighestStepsToNextCount(
                    pageTable, pageReferences, cursor)
                pageTable[indexToReplace] = pageNumber
                pass
            else:
                pageTable.append(pageNumber)

    return pageFaults

# For Test Script


FIFO = fifo
LRU = lru
OPT = opt
