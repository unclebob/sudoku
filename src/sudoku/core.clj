(ns sudoku.core)

(defn is-column-valid? [rank column]
  (and
    (= (* rank rank) (count column))
    (let [numbers (remove nil? column)]
      (= (count numbers) (count (set numbers))))))

(defn is-valid-board? [rank board]
  (and (= 1 (count board))
       (= 1 (count (board 0)))
       (condp = ((board 0) 0)
         1 true
         nil true
         false)))

(defn is-solved-board? [rank board]
  (= board [[1]]))

(defn solve-board [rank board]
  [[1]])