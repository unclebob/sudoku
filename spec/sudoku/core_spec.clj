(ns sudoku.core-spec
  (:require [speclj.core :refer :all]
            [sudoku.core :refer :all]))

(defn is-valid-board [rank board]
  true)

(defn solve-board [rank board]
  [[1]])


;This is the degenerate case.  A sudoku board of rank 1.  Such a board has only one possible solution
;which is [[1]].
(describe
  "a rank 1 game"
  (context
    "it has only one solution [[1]]"
    (it "is valid"
        (should (is-valid-board 1 [[1]])))
    (it "should solve [[]]"
        (should= [[1]] (solve-board 1 [[]])))
    )
  )