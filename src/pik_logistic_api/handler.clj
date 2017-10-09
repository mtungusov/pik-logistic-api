(ns pik-logistic-api.handler
  (:require [clojure.tools.logging :as log]
            [ring.util.http-response :as response]
            [ring.middleware.format :refer [wrap-restful-format]]
            [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [pik-logistic-api.routes.home :refer [home-routes]]))

(defn wrap-formats [handler]
  (wrap-restful-format
    handler
    {:formats [:json-kw :transit-json]}))

(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        (log/error t)
        (response/internal-server-error "Something very bad has happened!")))))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes wrap-formats))
    (route/not-found
      (response/not-found "page not found"))))

(defn app [] (wrap-internal-error #'app-routes))
