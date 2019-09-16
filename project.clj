(defproject reagent-samples "0.1.0-SNAPSHOT"
  :description "Reagent samples using React UI components"
  :url "https://github.com/makinoshi"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [metosin/reitit "0.3.1"]]

  :source-paths ["src"]
  :resource-paths ["resources" "target/cljs"]
  :target-path "target/%s/"
  :clean-targets ^{:protect false} ["target"]

  :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min" ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test" ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" reagent-samples.test-runner]}

  :profiles {:provided {:dependencies [[org.clojure/clojurescript "1.10.520"]
                                       [reagent "0.8.1"]

                                       [cljsjs/semantic-ui-react "0.88.1-0"]
                                       [malesch/semantic-reagent "1.0.0" :exclusions [cljsjs/semantic-ui-react]]

                                       [cljsjs/material-ui "3.9.1-0"]
                                       [cljsjs/material-ui-icons "3.0.1-0"]

                                       [cljsjs/react-sortable-hoc "0.8.2-0"]]}
             :dev {:source-paths ["src-dev"]
                   :dependencies [[com.bhauman/figwheel-main "0.2.3"]
                                  [nrepl/nrepl "0.6.0"]
                                  [cider/piggieback "0.4.1"]]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}})
