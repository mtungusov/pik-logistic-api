(ns pik-logistic-api.core
  (:require [clojure.tools.logging :as log]
            [mount.core :as mount]
            [pik-logistic-api.config :refer [settings]]
            [org.httpkit.server :refer [run-server]]
            [ring.util.http-response :as response]
            [ring.middleware.format :refer [wrap-restful-format]])
  (:gen-class))

(defonce state (atom {}))

(defn init [args]
  (log/info "PIK logistic API starting...")
  (swap! state assoc :running true)
  (mount/start #'settings))

(defn stop []
  (swap! state assoc :running false)
  (log/info "Stopping...")
  (when-let [server (:server @state)]
    (server :timeout 1000)
    (log/info "Httpkit stopped"))
  (shutdown-agents)
  (Thread/sleep 1000)
  (log/info "Stopped!"))

(defn handler [req]
  (response/ok {:result {:status :ok}}))

(defn wrap-formats [handler]
  (wrap-restful-format
    handler
    {:formats [:json-kw :transit-json]}))

(defn start-server []
  (let [port (get-in settings [:server :port])]
    (swap! state assoc :server (run-server (-> #'handler wrap-formats) {:port port}))
    (log/info "Httpkit started. Listen at :" port)))


(defn -main [& args]
  (init args)
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. stop))
  (start-server))
