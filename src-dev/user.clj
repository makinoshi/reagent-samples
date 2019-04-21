(ns user
  (:require [figwheel.main.api :as fig]))

(defn fig-start []
  (fig/start {:mode :serve} "dev"))

(defn fig-stop []
  (fig/stop "dev"))

(defn cljs-repl []
  (fig/cljs-repl "dev"))
