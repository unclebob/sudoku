(ns sudoku.core)

(defn extract-row [row board]
  (board row))

(defn extract-column [column board]
  (map #(nth % column) board))

(defn extract-sector [rank [sector-column sector-row] board]
  (let [sector-rows (take rank (drop (* rank sector-row) board))]
    (flatten (map #(take rank (drop (* rank sector-column) %)) sector-rows))))

(defn is-grouping-valid? [rank column]
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