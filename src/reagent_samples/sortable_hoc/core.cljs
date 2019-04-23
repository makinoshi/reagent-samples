(ns reagent-samples.sortable-hoc.core
  (:require [cljsjs.react-sortable-hoc]
            [goog.object :as go]
            [reagent.core :as reagent]
            [reagent-samples.util :as util]))

(defonce items
  (reagent/atom nil))

(def sortable-handle
  (go/get js/SortableHOC "SortableHandle"))

(def sortable-element
  (go/get js/SortableHOC "SortableElement"))

(def sortable-container
  (go/get js/SortableHOC "SortableContainer"))

(def drag-handle
  (reagent/adapt-react-class
   (sortable-handle
    (fn []
      (reagent/as-element
       [:i
        {:class "fas fa-align-justify"
         :style {:margin-right ".3em"}}])))))

(def sortable-item
  (reagent/adapt-react-class
   (sortable-element
    (reagent/reactify-component
     (fn [{:keys [value]}]
       [:li [drag-handle] value])))))

(def sortable-list
  (reagent/adapt-react-class
   (sortable-container
    (reagent/reactify-component
     (fn [{:keys [items]}]
       [:ul
        (for [[value index] (map vector items (range))]
          [sortable-item {:key (str "item-" index)
                          :index index
                          :value value}])])))))

(defn vector-move [coll prev-index new-index]
  (let [items (into (subvec coll 0 prev-index)
                    (subvec coll (inc prev-index)))]
    (-> (subvec items 0 new-index)
        (conj (nth coll prev-index))
        (into (subvec items new-index)))))

(defn sortable-component [items]
  [sortable-list
   {:items @items
    :on-sort-start (fn [e]
                     (prn "onSortStart in"))
    :on-sort-end #(swap! items vector-move (go/get % "oldIndex") (go/get % "newIndex"))
    :use-drag-handle true}])

(defn- main []
  [:<>
   [sortable-component items]
   [:div [:a {:href "" :on-click #(do (.preventDefault %)
                                      (js/history.back))}
          "Back"]]])

(defn index [el]
  (util/append-css "https://use.fontawesome.com/releases/v5.8.1/css/all.css")
  (reset! items (vec (map (fn [i] (str "Item " i)) (range 6))))
  (reagent/render [main] el))
