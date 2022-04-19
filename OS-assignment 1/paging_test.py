
# Program written by Batandwa Mgutsi
# Student Number: MGTBAT001
#

from paging import countStepsToNextTouchForPage, fifo, getCandidateIndexForPageWithHighestStepsToNextCount, lru, opt


TEST_STRING = [7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1]
FRAME_COUNT = 3

assert countStepsToNextTouchForPage(
    2, [2, 3, 2, 4, 5], 1) == 1, "test countStepsToNextTouchForPage with cursor"
assert countStepsToNextTouchForPage(
    7, [2, 3, 0, 7, 5], 1) == 2, "test countStepsToNextTouchForPage after cursor but before end"
assert countStepsToNextTouchForPage(
    5, [2, 3, 0, 7, 5], 1) == 3, "test countStepsToNextTouchForPage end"

assert getCandidateIndexForPageWithHighestStepsToNextCount(
    [7, 0, 1], TEST_STRING, 3) == 0, "test getCandidateIndexForPageWithLowestStepsToNextCount"

assert fifo(FRAME_COUNT, TEST_STRING) == 15, "test FIFO"
assert lru(FRAME_COUNT, TEST_STRING) == 12, "test LRU"
assert opt(FRAME_COUNT, TEST_STRING) == 9, "test OPT"

print("ALL PASSED")
