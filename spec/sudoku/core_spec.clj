(ns sudoku.core-spec
  (:require [speclj.core :refer :all]
            [sudoku.core :refer :all]))

;This is the degenerate case.  A sudoku board of rank 1.  Such a board has only one possible solution
;which is [[1]].
(describe
  "a rank 1 game"
  (context
    "it has only one solution [[1]]"
    (it "is valid if solved or empty."
        (should (is-valid-board? 1 [[1]]))
        (should (is-valid-board? 1 [[nil]])))
    (it "is invalid if wrong size or digit other than 1"
        (should-not (is-valid-board? 1 [[1] [1]]))
        (should-not (is-valid-board? 1 [[1 1]]))
        (should-not (is-valid-board? 1 []))
        (should-not (is-valid-board? 1 [[]]))
        (should-not (is-valid-board? 1 [[2]])))
    (it "should detect the one solution"
        (should (is-solved-board? 1 [[1]]))
        (should-not (is-solved-board? 1 [[nil]])))
    (it "should solve [[]]"
        (should= [[1]] (solve-board 1 [[nil]])))))

(describe
  "a rank 2 game"
  )

