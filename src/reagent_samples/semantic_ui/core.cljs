(ns reagent-samples.semantic-ui.core
  (:require [cljsjs.semantic-ui-react :as sui]
            [goog.object :as go]
            [goog.dom :as gd]
            [goog.dom.TagName :as tag]
            [reagent.core :as reagent]))

;; Semantic UI Reagent components

(defn- ->c [c & more]
  (->> (cons c more)
       (keep name)
       (apply go/getValueByKeys sui)
       reagent/adapt-react-class))

(def container (->c :Container))

(def segment (->c :Segment))

(def divider (->c :Divider))

(def grid (->c :Grid))

(def grid-row (->c :Grid :Row))

(def grid-column (->c :Grid :Column))

(def header (->c :Header))

(def icon (->c :Icon))

(def button (->c :Button))

(def flag (->c :Flag))

(def input (->c :Input))

(def label (->c :Label))

;; Component adapted Reagent components

(defn- components-using-adapt-react-class []
  [container
   [header {:as "h1"} "Show components using reagent.core/adapt-react-class"]
   [grid {:columns 3 :divided true}
    [grid-row
     [grid-column "column 1"]
     [grid-column "column 2"]
     [grid-column "column 3"]]]
   [segment
    [grid
     [grid-row
      [grid-column
       [button "foo"]
       [button {:primary true} "bar"]
       [button {:secondary true} "baz"]]]]]
   [segment
    [grid {:columns 2 :relaxed "very"}
     [grid-column
      [:p "A B C"]
      [:p "D E F"]]
     [grid-column
      [:p "1 2 3"]
      [:p "4 5 6"]]]
    [divider {:vertical true} "AND"]]
   [segment
    [flag {:name "jp"}]]
   [segment
    [input {:placeholder "sample input"}]
    [input {:loading true :icon "user"}]
    [input {:error true}]]
   [segment
    [label "foo"]
    [label [icon {:name "mail"}] "Send"]
    [label {:as "a" :tag true :color "teal"} "TAG"]]])

;; Component using syntax sugar

(defn- components-using-shortern-syntax []
  [:> sui/Container {:style {:margin-top "5em"}}
   [:> sui/Header {:as "h1"}
    "Show components using " [:code "[:> ... ]"] " syntac"]])

(defn- back-link []
  [container {:style {:margin-top "2em"}}
   [:a {:href "" :on-click #(do (.preventDefault %)
                                (js/history.back))}
    [icon {:name "arrow left"}]
    "Back"]])

(defn- main []
  [:<>
   [components-using-adapt-react-class]
   [components-using-shortern-syntax]
   [back-link]])

(defn- append-css []
  (let [head (.querySelector js/document "head")
        css (gd/createDom tag/LINK #js {:rel "stylesheet"
                                        :class "inserted-css"
                                        :href "//cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css"})
        inserted-css (gd/findNodes head (fn [node]
                                          (and (= (go/get node "tagName") "LINK")
                                               (= (go/get node "className") "inserted-css"))))]
    (doseq [node (array-seq inserted-css)]
      (gd/removeNode node))
    (gd/appendChild head css)))

(defn index [el]
  (append-css)
  (reagent/render [main] el))
