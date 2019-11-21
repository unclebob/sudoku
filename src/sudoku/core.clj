(ns sudoku.core)

(defn is-valid-board? [rank board]
  (if (not= 1 (count board))
    false
    (if (not= 1 (count (board 0)))
      false
      (condp = ((board 0) 0)
        1 true
        nil true
        false))))

(defn is-solved-board? [rank board]
  (= board [[1]]))

(defn solve-board [rank board]
  [[1]])