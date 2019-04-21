(ns ^:figwheel-hooks reagent-samples.core
  (:require [goog.dom :as gdom]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.core :as reagent :refer [atom]]
            [reagent-samples.semantic-ui.core :as semantic-ui]
            [reitit.core :as r])
  (:import goog.history.Html5History))

(defn multiply [a b] (* a b))

(defn get-app-element []
  (gdom/getElement "app"))

(defn main-component []
  [:div
   [:h1 "Defined UI lists"]
   [:ul
    [:li [:a {:href "#/semantic-ui"} "semantic ui"]]]])

(defn index [el]
  (reagent/render-component [main-component] el))

(def router
  (r/router [["" index]
             ["/semantic-ui" semantic-ui/index]]))

(defonce path (atom nil))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen EventType/NAVIGATE
                   (fn [event]
                     (js/console.log (.-token event))
                     (reset! path (.-token event))))
    (.setEnabled true)))

(defn- render-page [el]
  (when-let [{:keys [result]} (r/match-by-path router new-path)]
    (result el)))

(defn main []
  (when-let [el (get-app-element)]
    (add-watch path :route
               (let []
                 (fn [_ _ old-path new-path]
                   (when (not= old-path new-path)
                     (render-page el)))))
    (hook-browser-navigation!)
    (reset! path js/location.hash)))

(main)

(defn ^:after-load on-reload []
  (render-page (get-app-element)))
