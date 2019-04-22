(ns reagent-samples.semantic-ui.core
  (:require [cljsjs.semantic-ui-react :as sui]
            [goog.object :as go]
            [goog.dom :as gd]
            [goog.dom.TagName :as tag]
            [reagent.core :as reagent])
  (:refer-clojure :exclude [list]))

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

(def list (->c :List))

(def list-item (->c :List :Item))

(def list-icon (->c :List :Icon))

(def list-content (->c :List :Content))

(def list-header (->c :List :Header))

(def list-description (->c :List :Description))

(def dimmer (->c :Dimmer))

(def loader (->c :Loader))

(def placeholder (->c :Placeholder))

(def placeholder-header (->c :Placeholder :Header))

(def placeholder-line (->c :Placeholder :Line))

(def placeholder-paragraph (->c :Placeholder :Paragraph))

(def modal (->c :Modal))

(def modal-header (->c :Modal :Header))

(def modal-content (->c :Modal :Content))

(def modal-description (->c :Modal :Description))

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
    [flag {:name "jp"}]
    [flag {:name "us"}]
    [flag {:name "cn"}]]
   [segment
    [input {:placeholder "sample input"}]
    [input {:loading true :icon "user"}]
    [input {:error true}]]
   [segment
    [label "foo"]
    [label [icon {:name "mail"}] "Send"]
    [label {:as "a" :tag true :color "teal"} "TAG"]]
   [segment
    [list {:divided true :relaxed true}
     [list-item
      [list-icon {:name "github" :size "large" :verticalAlign "middle"}]
      [list-content
       [list-header {:as "a" :href "https://github.com/Semantic-Org/Semantic-UI" :target "_blank"}
        "Semantic-Org/Semantic-UI"]
       [list-description "Using this CSS framework"]]]]]
   [segment
    [dimmer {:active true :inverted false}
     [loader "Loading.."]]
    [:p "hoge hoge hoge"]
    [:p "fuga fuga fuga"]]
   [segment
    [placeholder
     [placeholder-header {:image true}
      [placeholder-line]
      [placeholder-line]]
     [placeholder-paragraph
      [placeholder-line]
      [placeholder-line]
      [placeholder-line]]]]
   [segment
    [modal {:trigger (reagent/as-element [button "Show Modal"])}
     [modal-header "Modal header"]
     [modal-content
      "foo"
      [modal-description
       [header "bar"]
       [:p "hoge"]]]]]])

;; Component using syntax sugar

(defn- components-using-shortern-syntax []
  [:> sui/Container {:style {:margin-top "5em"}}
   [:> sui/Header {:as "h1"}
    "Show components using " [:code "[:> ... ]"] " syntac"]
   [:> sui/Segment {:placeholder true}
    [:> sui/Header {:icon true}
     [:> sui/Icon {:name "search"}]
     "We don't have any documents matching your query."]
    [:> sui/Segment.Inline
     [:> sui/Button {:primary true} "Color Query"]
     [:> sui/Button "Add document"]]]
   [:> sui/Segment
    [:> sui/Step.Group {:ordered true}
     [:> sui/Step {:completed true}
      [:> sui/Step.Content
       [:> sui/Step.Title "Shipping"]
       [:> sui/Step.Description "Choose your shipping options"]]]
     [:> sui/Step {:completed true}
      [:> sui/Step.Content
       [:> sui/Step.Title "Billing"]
       [:> sui/Step.Description "Enter billing information"]]]
     [:> sui/Step {:active true}
      [:> sui/Step.Content
       [:> sui/Step.Title "Confirm Order"]]]]]])

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
