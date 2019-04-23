(ns reagent-samples.util
  (:require [goog.object :as go]
            [goog.dom :as gd]
            [goog.dom.TagName :as tag]
            [reagent.core :as reagent]))

(defn adapt-react-class
  [js-obj c & more]
  (->> (cons c more)
       (keep name)
       (apply go/getValueByKeys js-obj)
       reagent/adapt-react-class))

(defn append-css [css-path & paths]
  (let [head (.querySelector js/document "head")
        inserted-css (gd/findNodes head (fn [node]
                                          (and (= (go/get node "tagName") "LINK")
                                               (= (go/get node "className") "inserted-css"))))]
    (doseq [node (array-seq inserted-css)]
      (gd/removeNode node))
    (doseq [path (cons css-path paths)
            :let [css (gd/createDom tag/LINK #js {:rel "stylesheet"
                                                  :class "inserted-css"
                                                  :href path})]]
      (gd/appendChild head css))))
