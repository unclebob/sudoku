(ns sudoku.core-spec
  (:require [speclj.core :refer :all]
            [sudoku.core :refer :all]))

(let [empty-row (repeat 9 :x)
      empty-board (repeat 9 empty-row)]
  (describe
    "add-to-board"
    (it "should add a list of tokens to the board"
        (let [expected-row (concat [:0 :1] (repeat 7 :x))
              expected-board (concat expected-row (repeat 8 empty-row))]
          (should= expected-board (add-to-board empty-board [[0 0] :0
                                                             [0 1] :1])))))
  (describe
    "a valid board"
    (it "is valid if empty"
        (should (valid? empty-board))))

  (describe
    "an invalid board"
    (it "is invalid when there are any repeats in a row"
        (should-not (valid? (add-to-board empty-board [[0 0] :0
                                                       [0 1] :1])))
        ))
  )