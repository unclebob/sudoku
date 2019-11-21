(ns sudoku.core-spec
  (:require [speclj.core :refer :all]
            [sudoku.core :refer :all]))

(def N nil)

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
  (context
    "has groupings consisting of columns, rows, and sectors"
    (let [board [[1 2 3 4]
                 [2 3 4 1]
                 [4 3 2 1]
                 [3 2 1 4]]]
      (it "should extract rows"
          (should= [1 2 3 4] (extract-row 0 board))
          (should= [2 3 4 1] (extract-row 1 board)))
      (it "should extract columns"
          (should= [1 2 4 3] (extract-column 0 board))
          (should= [2 3 3 2] (extract-column 1 board)))
      (it "should extract sectors"
          (should= [1 2 2 3] (extract-sector 2 [0 0] board))
          (should= [3 4 4 1] (extract-sector 2 [1 0] board))
          (should= [2 1 1 4] (extract-sector 2 [1 1] board)))))
  (context
    "a grouping"
    (it "is invalid if the wrong size."
        (should-not (is-valid-grouping? 2 []))
        (should-not (is-valid-grouping? 2 [1 2 3 4 nil])))
    (it "is invalid if there are duplicate numbers"
        (should-not (is-valid-grouping? 2 [1 1 1 1]))
        (should-not (is-valid-grouping? 2 [1 1 2 3]))
        (should-not (is-valid-grouping? 2 [1 N 2 1])))
    (it "is valid if correct size and no duplicates."
        (should (is-valid-grouping? 2 [1 2 3 4]))
        (should (is-valid-grouping? 2 [1 2 N N])))
    (it "is solved if valid and no nils."
        (should (is-solved-grouping? 2 [1 2 3 4]))
        (should-not (is-solved-grouping? 2 [1 2 N 4]))))

  (context
    "can determine if a board is valid"
    (it "is valid if solved"
        (should (is-valid-board? 2 [[1 2 3 4]
                                    [3 4 1 2]
                                    [2 3 4 1]
                                    [4 1 2 3]]))))

  (context
    "Solutions can be detected."
    (it "should see this as solved."
        (should (is-solved-board? 2 [[1 2 3 4]
                                     [3 4 1 2]
                                     [2 3 4 1]
                                     [4 1 2 3]])))

    (it "should not see a board with nils as solved"
        (should-not (is-solved-board? 2 [[1 N 3 4]
                                         [3 4 1 2]
                                         [2 3 4 1]
                                         [4 1 2 3]])))
    (it "should not see an invalid board as solved"
        (should-not (is-solved-board? 2 [[1 4 3 4]
                                         [3 2 1 2]
                                         [2 3 4 1]
                                         [4 1 2 3]]))))
  )

(describe
  "The sudoku solver"
  (context
    "It can identify missing cells"
    (it "will find the missing cell in a rank 1 board"
        (should= [[0 0]] (find-missing-cells 1 [[N]]))
        (should= [] (find-missing-cells 1 [[1]])))
    (it "will find missing dells in a rank 2 board"
        (should= (set [[2 1] [1 2]])
                 (set (find-missing-cells 2 [[1 4 3 4]
                                             [3 4 N 2]
                                             [2 N 4 1]
                                             [4 1 2 3]]))))
    )

  (context
    "It can find the possible values for the missing cells"
    (it "will find the only possible value for rank 1"
        (should= #{1} (find-possible-values 1 [0 0] [[N]])))
    (it "will find the missing values for rank 2"
        (should= #{2} (find-possible-values 2 [1 0] [[1 N 3 4]
                                                     [3 4 1 2]
                                                     [2 3 4 1]
                                                     [4 1 2 3]]))
        (should= #{4 2} (find-possible-values 2 [2 2] [[1 2 3 4]
                                                       [3 4 1 2]
                                                       [N 3 N N]
                                                       [4 1 N N]]))
        )

    )

  )


