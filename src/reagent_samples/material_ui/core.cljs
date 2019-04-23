(ns reagent-samples.material-ui.core
  (:require [material-ui]
            [material-ui-icons]
            [reagent.core :as reagent]
            [reagent-samples.util :as util])
  (:refer-clojure :exclude [list]))

(def ^:private ->c
  (partial util/adapt-react-class js/MaterialUI))

;; Material Component

(def grid (->c :Grid))

(def paper (->c :Paper))

(def typography (->c :Typography))

(def button (->c :Button))

(def link (->c :Link))

(def modal (->c :Modal))

(def app-bar (->c :AppBar))

(def toolbar (->c :Toolbar))

(def icon-button (->c :IconButton))

(def drawer (->c :Drawer))

(def list (->c :List))

(def list-item (->c :ListItem))

(def list-item-text (->c :ListItemText))

(def list-item-icon (->c :ListItemIcon))

(def divider (->c :Divider))

(def circular-progress (->c :CircularProgress))

(def dialog (->c :Dialog))

(def dialog-title (->c :DialogTitle))

;; Material Icon

(def ^:private ->ci
  (partial util/adapt-react-class js/MaterialUIIcons))

(def menu-icon (->ci :Menu))

(def chevron-left-icon (->ci :ChevronLeft))

(def chevron-right-icon (->ci :ChevronRight))

(def inbox-icon (->ci :MoveToInbox))

(def mail-icon (->ci :Mail))

;; Components

(defn- modal-component []
  (let [open? (reagent/atom false)]
    (fn []
      [:<>
       [button {:on-click (fn [_] (swap! open? not))}
        "Open Modal"]
       [modal {:open @open?
               :on-close #(reset! open? false)
               :style {:position "absolute"
                       :outline "none"}}
        [:div
         [typography {:id "modal-title" :variant "h6"}
          "text in normal"]
         "modal now"]]])))

(defn- drawer-component [open?]
  [drawer {:variant "persistent"
           :anchor "left"
           :open @open?}
   [:div {:style {:display "flex"
                  :align-items "center"
                  :padding "0 8px"
                  :justify-content "filex-end"}}
    [icon-button {:on-click (fn [_] (swap! open? not))}
     [chevron-left-icon]]]
   [list
    [list-item
     [list-item-icon [inbox-icon]]
     [list-item-text "foo"]]
    [list-item
     [list-item-icon [mail-icon]]
     [list-item-text "bar"]]]
   [divider]])

(defn- app-bar-component []
  (let [drawer-open? (reagent/atom false)]
    (fn []
      [:<>
       [app-bar {:position "static"}
        [toolbar
         [icon-button {:color "inherit"
                       :aria-label "Menu"
                       :style {:margin-left -12 :margin-right 20}
                       :on-click (fn [_] (swap! drawer-open? not))}
          [menu-icon]]
         [typography {:variant "h6" :color "inherit" :style {:flex-grow 1}}
          "News"]
         [button {:color "inherit"} "Login"]]]
       [drawer-component drawer-open?]])))

(defn- dialog-component []
  (let [open? (reagent/atom false)]
    (fn []
      [:<>
       [button {:on-click #(swap! open? not)} "Open button"]
       [dialog {:open @open?
                :on-close #(reset! open? false)}
        [dialog-title "Dialog Shown"]
        [list
         [list-item
          [list-item-text "foo"]
          [list-item-text "bar"]]]]])))

(defn- back-link []
  [link {:href "" :on-click #(do (.preventDefault %)
                                 (js/history.back))}
   "Back"])

(defn- main []
  [:main {:style {:flex-grow 1}}
   [app-bar-component]
   [grid {:container true :spacing 24}
    [grid {:item true :xs 12}
     [typography {:variant "h1"} "Show components using Material-UI"]]
    [grid {:item true :xs 12}
     [paper "xs=12"]]
    [grid {:item true :xs 6}
     [paper "xs=6"]]
    [grid {:item true :xs 6}
     [paper "xs=6"]]
    [grid {:item true :xs 12}
     [paper [modal-component]]]
    [grid {:item true :xs 12}
     [paper
      [circular-progress]
      [circular-progress {:color "secondary"}]]]
    [grid {:item true :xs 12}
     [paper
      [dialog-component]]]
    ]
   [back-link]
   ])

(defn index [el]
  (util/append-css "https://fonts.googleapis.com/css?family=Roboto:300,400,500"
                   "https://fonts.googleapis.com/icon?family=Material+Icons")
  (reagent/render [main] el))
