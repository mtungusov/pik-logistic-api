(ns pik-logistic-api.core
  (:require [clojure.tools.logging :as log]
            [mount.core :as mount]
            [pik-logistic-api.config :refer [settings]])
  (:gen-class))

(def state (atom {}))

(defn init [args]
  (log/info "PIK logistic API starting...")
  (swap! state assoc :running true)
  (mount/start #'settings))

(defn stop []
  (swap! state assoc :running false)
  (log/info "Stopping...")
  (shutdown-agents)
  (Thread/sleep 1000)
  (log/info "Stopped!"))

(defn -main [& args]
  (init args)
  (log/info (identity settings))
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. stop))
  (while (:running @state)
    (log/info "tick")
    (Thread/sleep 1000)))
