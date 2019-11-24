(ns sudoku.core
  (:require [clojure.set :as set]))

(defn extract-row [row board]
  (board row))

(defn extract-column [column board]
  (map #(nth % column) board))

(defn extract-sector [rank [sector-column sector-row] board]
  (let [sector-rows (take rank (drop (* rank sector-row) board))]
    (flatten (map #(take rank (drop (* rank sector-column) %)) sector-rows))))

(defn find-missing-cells [rank board]
  (let [group-size (* rank rank)
        coords (for [col (range group-size)
                     row (range group-size)]
                 (if (nil? ((board row) col))
                   [col row]
                   nil))]
    (filter some? coords)))

(defn find-possible-values [rank [col row] board]
  (let [possible (set (drop 1 (range (inc (* rank rank)))))
        column-grouping (set (extract-column col board))
        row-grouping (set (extract-row row board))
        sector-col (quot col rank)
        sector-row (quot row rank)
        sector-grouping (set (extract-sector rank [sector-col sector-row] board))]
    (set/difference possible column-grouping row-grouping sector-grouping)))

(defn set-cell [board [col row] value]
  (assoc-in board [row col] value)
  )

(declare solve-board-task)

(defn try-possible-values [cell possible-values rank board]
  (let [grouped-solutions (for [value possible-values]
                            (let [trial-board (set-cell board cell value)]
                              (solve-board-task rank trial-board [])))]
    (apply concat grouped-solutions))
  )

(defn solve-board-task [rank board solutions]
  (let [missing-cells (find-missing-cells rank board)]
    (if (empty? missing-cells)
      (conj solutions board)
      (let [first-missing-cell (first missing-cells)
            values (find-possible-values rank first-missing-cell board)
            new-solutions (try-possible-values first-missing-cell values rank board)]
        (concat solutions new-solutions)))))

(defn solve-board [rank board]
  (solve-board-task rank board []))
