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
    (it "should solve [[N]]"
        (should= [[[1]]] (solve-board 1 [[N]])))))

(describe
  "a rank 2 game"
  )(context
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

  (it
    "can set the cell of a board"
    (should= [[1]] (set-cell [[N]] [0 0] 1))
    (should= [[1 2 3 4]
              [2 3 4 1]
              [4 3 2 1]
              [2 4 1 3]] (set-cell [[1 2 3 4]
                                    [2 3 1 1]
                                    [4 3 2 1]
                                    [2 4 1 3]] [2 1] 4)))

  (it
    "can solve the rank 2 board"
    (should= [[[1 2 3 4]
               [3 4 1 2]
               [2 3 4 1]
               [4 1 2 3]]] (solve-board 2 [[1 2 3 4]
                                           [3 4 1 2]
                                           [N 3 N 1]
                                           [4 1 N 3]])))

  (it
    "can solve the rank 2 boards with multiple solutions"
    (should= [[[1 2 3 4]
               [3 4 1 2]
               [4 3 2 1]
               [2 1 4 3]]
              [[1 2 3 4]
               [3 4 1 2]
               [2 3 4 1]
               [4 1 2 3]]] (solve-board 2 [[1 2 3 4]
                                           [3 4 1 2]
                                           [N 3 N 1]
                                           [N 1 N 3]])))

  (it
    "can solve rank 3"
    (should= [[[4 3 5 2 6 9 7 8 1]
               [6 8 2 5 7 1 4 9 3]
               [1 9 7 8 3 4 5 6 2]
               [8 2 6 1 9 5 3 4 7]
               [3 7 4 6 8 2 9 1 5]
               [9 5 1 7 4 3 6 2 8]
               [5 1 9 3 2 6 8 7 4]
               [2 4 8 9 5 7 1 3 6]
               [7 6 3 4 1 8 2 5 9]]] (solve-board 3 [[N N N 2 6 N 7 N 1]
                                                     [6 8 N N 7 N N 9 N]
                                                     [1 9 N N N 4 5 N N]
                                                     [8 2 N 1 N N N 4 N]
                                                     [N N 4 6 N 2 9 N N]
                                                     [N 5 N N N 3 N 2 8]
                                                     [N N 9 3 N N N 7 4]
                                                     [N 4 N N 5 N N 3 6]
                                                     [7 N 3 N 1 8 N N N]])))

  ;(it
  ;  "can solve not fun problems"
  ;  (should= [] (solve-board 3 [[N 2 N N N N N N N]
  ;                              [N N N 6 N N N N 3]
  ;                              [N 7 4 N 8 N N N N]
  ;                              [N N N N N 3 N N 2]
  ;                              [N 8 N N 4 N N 1 N]
  ;                              [6 N N 5 N N N N N]
  ;                              [N N N N 1 N 7 8 N]
  ;                              [5 N N N N 9 N N N]
  ;                              [N N N N N N N 4 N]])))

  )


