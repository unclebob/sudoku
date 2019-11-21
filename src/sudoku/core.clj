(ns sudoku.core)

(defn extract-row [row board]
  (board row))

(defn extract-column [column board]
  (map #(nth % column) board))

(defn extract-sector [rank [sector-column sector-row] board]
  (let [sector-rows (take rank (drop (* rank sector-row) board))]
    (flatten (map #(take rank (drop (* rank sector-column) %)) sector-rows))))

(defn is-valid-grouping? [rank grouping]
  (and
    (= (* rank rank) (count grouping))
    (let [numbers (remove nil? grouping)]
      (= (count numbers) (count (set numbers))))))

(defn is-solved-grouping? [rank grouping]
  (and
    (every? some? grouping)
    (is-valid-grouping? rank grouping)))

(defn is-valid-board? [rank board]
  (and (= 1 (count board))
       (= 1 (count (board 0)))
       (condp = ((board 0) 0)
         1 true
         nil true
         false)))

(defn is-solved-board? [rank board]
  (let [rows (for [row (range (* rank rank))]
               (extract-row row board))
        cols (for [col (range (* rank rank))]
               (extract-column col board))
        sectors (for [row (range rank) col (range rank)]
                  (extract-sector rank [col row] board))
        groupings (concat rows cols sectors)]
    (every? #(is-solved-grouping? rank %) groupings)))

(defn solve-board [rank board]
  [[1]])