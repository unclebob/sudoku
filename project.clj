(defproject sudoku "0.1.0-SNAPSHOT"
  :description "Sudoku Solver"
  :license {:name "No Restrictions"}
  :main sudoku.core
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"])
