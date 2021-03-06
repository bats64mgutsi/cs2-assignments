# Program written by Batandwa Mgutsi
# Student Number: MGTBAT001
#
# usage: python3 <number_of_page_references>
#

from collections import deque
from heapq import heapify, heappop, heappush
from random import randint, random
import sys
from typing import List, Tuple

# Utils ---------

currentTimeStamp = 0


def getCurrentTimestamp() -> int:
    """Returns a simulated timestamp"""
    global currentTimeStamp
    currentTimeStamp += 1
    return currentTimeStamp


def getLRUPageTableEntryIndex(pageNumber: int, pageTable: List[Tuple[float, int]]) -> int:
    """Returns the index of the page entry for the page with the given number."""
    for index, (_, entryPageNumber) in enumerate(pageTable):
        if entryPageNumber == pageNumber:
            return index

    return -1


def lruPageTableEntryExist(pageNumber: int, pageTable: List[Tuple[float, int]]) -> bool:
    """Returns true if, and only if, a page with the given page number exists in the given pageTable."""
    return getLRUPageTableEntryIndex(pageNumber, pageTable) != -1


def countStepsToNextTouchForPage(pageNumber: int, inReferenceString: List[int], cursor: int) -> int:
    """Returns the number of page references between the current index given by cursor till the next
       reference of the page with the given page number."""
    if cursor >= len(inReferenceString):
        return 0

    forwardCollection = inReferenceString[cursor:]
    for subIndex, nextPageNumber in enumerate(forwardCollection):
        if nextPageNumber == pageNumber:
            return subIndex

    return len(forwardCollection)


def getCandidateIndexForPageWithHighestStepsToNextCount(candidatePageNumbers: List[int], inReferenceString: List[int], cursor: int) -> int:
    """Returns the index, in candidatePageNumbers, of the page that will not be used for the
       longest time in the given reference string from the current index given by cursor."""
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

# Actual algorithms ------------


def fifo(frameCount: int, pageReferences: List[int]) -> int:
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


def lru(frameCount: int, pageReferences: List[int]) -> int:
    """Runs the LRU algorithm on the given test string and returns the number of page faults"""
    pageTable: List[Tuple[float, int]] = []
    pageFaults = 0
    for pageNumber in pageReferences:
        if lruPageTableEntryExist(pageNumber, pageTable):
            # Touch the entry
            entryIndex = getLRUPageTableEntryIndex(pageNumber, pageTable)
            pageTable[entryIndex] = (getCurrentTimestamp(), pageNumber)
            heapify(pageTable)
        else:
            pageFaults += 1

            if len(pageTable) == frameCount:
                heappop(pageTable)

            heappush(pageTable, (getCurrentTimestamp(), pageNumber))

    return pageFaults


def opt(frameCount: int, pageReferences: List[int]) -> int:
    """Runs the OPT algorithm on the given test string and returns the number of page faults"""
    pageTable: List[int] = []
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

# For Test Script ------------


FIFO = fifo
LRU = lru
OPT = opt

# Main --------


def main():
    size = 3
    numberOfPageReferences = int(sys.argv[1])
    def f(_): return randint(0, 9)
    pages = list(map(f, range(numberOfPageReferences)))
    print("test string:", pages)
    print("FIFO", FIFO(size, pages), "page faults.")
    print("LRU", LRU(size, pages), "page faults.")
    print("OPT", OPT(size, pages), "page faults.")


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python paging.py [number of pages]")
    else:
        main()
