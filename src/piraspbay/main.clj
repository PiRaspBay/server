(ns piraspbay.main
  (:gen-class)
  (:use [piraspbay.config :only [app-configs cfg]]
        [clojure.tools.cli :only [cli]]
        ;; database access
        ;; [org.httpkit.dbcp :only [use-database! close-database!]]
        [org.httpkit.server :only [run-server]]
        [piraspbay.routes :only [app]]
        [clojure.tools.logging :only [info]])
  (:require [monger.core :as mg]))

(defn- to-int [s] (Integer/parseInt s))

(defonce server (atom nil))

(defn start-server []
  ;; stop it if started, for run -main multi-times in repl
  (when-not (nil? @server) (@server))
  (let [config (cfg :mongodb)]
    (mg/connect! {:host (:host config) :port (:port config)})
    (mg/set-db! (mg/get-db (:db config))))
  (reset! server (run-server (app) {:port (cfg :port)
                                    :thread (cfg :thread)})))

(defn -main [& args]
  (let [[options _ banner]
        (cli args
             ["-p" "--port" "Port to listen" :default 8080 :parse-fn to-int]
             ["--thread" "Http worker thread count" :default 4 :parse-fn to-int]
             ["--profile" "dev or prod" :default :dev :parse-fn keyword]
             ["--[no-]help" "Print this help"])]
    (when (:help options) (println banner) (System/exit 0))
    ;; config can be accessed by (cfg :key)
    (swap! app-configs merge options)
    (start-server)
    (info (str "server started. listen on 0.0.0.0@" (cfg :port)))))
