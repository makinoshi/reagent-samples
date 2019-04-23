(ns ^:figwheel-hooks reagent-samples.core
  (:require [goog.dom :as gdom]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.core :as reagent :refer [atom]]
            [reagent-samples.semantic-ui.core :as semantic-ui]
            [reagent-samples.material-ui.core :as material-ui]
            [reagent-samples.sortable-hoc.core :as sortable-hoc]
            [reagent-samples.util :as util]
            [reitit.core :as r])
  (:import goog.history.Html5History))

(enable-console-print!)

(defn multiply [a b] (* a b))

(defn get-app-element []
  (gdom/getElement "app"))

(defn main-component []
  [:div
   [:h1 "Defined UI lists"]
   [:ul
    [:li [:a {:href "#/semantic-ui"} "Semantic UI"]]
    [:li [:a {:href "#/material-ui"} "Material UI"]]
    [:li [:a {:href "#/react-sortable-hoc"} "React Sortable HOC"]]]])

(defn index [el]
  (util/remove-css)
  (reagent/render-component [main-component] el))

(def router
  (r/router [["" index]
             ["/semantic-ui" semantic-ui/index]
             ["/material-ui" material-ui/index]
             ["/react-sortable-hoc" sortable-hoc/index]]))

(defonce path (atom nil))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen EventType/NAVIGATE
                   (fn [event]
                     (js/console.log (.-token event))
                     (reset! path (.-token event))))
    (.setEnabled true)))

(defn- render-page [el new-path]
  (when-let [{:keys [result]} (r/match-by-path router new-path)]
    (result el)))

(defn main []
  (when-let [el (get-app-element)]
    (add-watch path :route
               (let []
                 (fn [_ _ old-path new-path]
                   (when (not= old-path new-path)
                     (render-page el new-path)))))
    (hook-browser-navigation!)
    (reset! path js/location.hash)))

(main)

(defn ^:after-load on-reload []
  (render-page (get-app-element) js/location.hash))
