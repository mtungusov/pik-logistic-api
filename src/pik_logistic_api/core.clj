(ns pik-logistic-api.core
  (:require [clojure.tools.logging :as log]
            [mount.core :as mount]
            [org.httpkit.server :refer [run-server]]
            [pik-logistic-api.config :refer [settings]]
            [pik-logistic-api.handler :as handler])
  (:gen-class))

(defonce state (atom {}))

(defn init [args]
  (log/info "PIK logistic API Server starting...")
  (swap! state assoc :running true)
  (mount/start #'settings))

(defn stop []
  (swap! state assoc :running false)
  (log/info "Stopping...")
  (when-let [server (:server @state)]
    (server :timeout 1000)
    (log/info "HTTP Kit stopped"))
  (shutdown-agents)
  (Thread/sleep 1000)
  (log/info "Stopped!"))

(defn start-server []
  (let [port (get-in settings [:server :port])]
    (swap! state assoc :server (run-server (handler/app) {:port port}))
    (log/info "HTTP Kit started. Listen at :" port)))


(defn -main [& args]
  (init args)
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. stop))
  (start-server))
