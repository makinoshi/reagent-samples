(defproject reagent-samples "0.1.0-SNAPSHOT"
  :description "Reagent samples using React UI components"
  :url "https://github.com/makinoshi"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]
                 [metosin/reitit "0.3.1"]
                 [reagent "0.8.1"]

                 [cljsjs/semantic-ui-react "0.84.0-0"]
                 [cljsjs/material-ui "3.9.1-0"]
                 [cljsjs/material-ui-icons "3.0.1-0"]
                 [cljsjs/react-sortable-hoc "0.8.2-0"]]

  :source-paths ["src"]

  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" reagent-samples.test-runner]}

  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.1.9"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]
                                  [nrepl/nrepl "0.6.0"]
                                  [cider/piggieback "0.4.0"]]
                   :repl-options {:init-ns user
                                  :nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   :source-paths ["src-dev"]}})
