(ns sudoku.core
  (:require [clojure.set :as set]))

(defn extract-row [row board]
  (board row))

(defn extract-column [column board]
  (map #(nth % column) board))

(defn extract-sector [rank [sector-column sector-row] board]
  (let [sector-rows (take rank (drop (* rank sector-row) board))]
    (flatten (map #(take rank (drop (* rank sector-column) %)) sector-rows))))

(defn is-valid-grouping? [rank grouping]
  (let [group-size (* rank rank)]
    (and
      (= group-size (count grouping))
      (let [numbers (remove nil? grouping)]
        (and
          (= (count numbers) (count (set numbers)))
          (every? #(and (> % 0) (<= % group-size)) numbers))))))

(defn is-solved-grouping? [rank grouping]
  (and
    (every? some? grouping)
    (is-valid-grouping? rank grouping)))

(defn is-valid-dimensions? [rank board]
  (let [expected-size (* rank rank)]
    (and
      (= expected-size (count board))
      (every? #(= expected-size (count %)) board))))

(defn get-all-groupings [rank board]
  (let [rows (for [row (range (* rank rank))]
               (extract-row row board))
        cols (for [col (range (* rank rank))]
               (extract-column col board))
        sectors (for [row (range rank) col (range rank)]
                  (extract-sector rank [col row] board))]
    (concat rows cols sectors)
    ))

(defn is-valid-board? [rank board]
  (and
    (is-valid-dimensions? rank board)
    (every? #(is-valid-grouping? rank %) (get-all-groupings rank board))))

(defn is-solved-board? [rank board]
  (every? #(is-solved-grouping? rank %) (get-all-groupings rank board)))

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

(defn associate-possible-values [rank board]
  (let [missing-cells (find-missing-cells rank board)
        associated-values (map #(vector % (find-possible-values rank % board)) missing-cells)]
    (set associated-values)))

(defn solve-board [rank board]
  [[1]])