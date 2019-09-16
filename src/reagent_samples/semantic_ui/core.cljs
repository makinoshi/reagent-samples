(ns reagent-samples.semantic-ui.core
  (:require [cljsjs.semantic-ui-react]
            [goog.object :as go]
            [reagent.core :as reagent]
            [reagent-samples.util :as util]
   ;; It cannot use because it does not work with advanced compilation
            #_[semantic-reagent.core :as sui]
            )
  (:refer-clojure :exclude [list]))

;; Semantic UI Reagent components

(def ^:private ->c
  (partial util/adapt-react-class js/semanticUIReact))

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
   #_[sui/Header {:as "h1"} "From semantic-reagent.core"]
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
  (let [->c (fn [s & more]
              (apply go/getValueByKeys js/semanticUIReact (cons s more)))]
    [:> (->c "Container") {:style {:margin-top "5em"}}
     [:> (->c "Header") {:as "h1"}
      "Show components using " [:code "[:> ... ]"] " syntac"]
     [:> (->c "Segment") {:placeholder true}
      [:> (->c "Header") {:icon true}
       [:> (->c "Icon") {:name "search"}]
       "We don't have any documents matching your query."]
      [:> (->c "Segment" "Inline")
       [:> (->c "Button") {:primary true} "Color Query"]
       [:> (->c "Button") "Add document"]]]
     [:> (->c "Segment")
        [:> (->c "Step" "Group") {:ordered true}
         [:> (->c "Step") {:completed true}
          [:> (->c "Step" "Content")
           [:> (->c "Step" "Title") "Shipping"]
           [:> (->c "Step" "Description") "Choose your shipping options"]]]
         [:> (->c "Step") {:completed true}
          [:> (->c "Step" "Content")
           [:> (->c "Step" "Title") "Billing"]
           [:> (->c "Step" "Description") "Enter billing information"]]]
         [:> (->c "Step") {:active true}
          [:> (->c "Step" "Content")
           [:> (->c "Step" "Title") "Confirm Order"]]]]]]))

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

(defn index [el]
  (util/append-css "//cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css")
  (reagent/render [main] el))
