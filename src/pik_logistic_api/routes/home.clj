(ns pik-logistic-api.routes.home
  (:require [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]))

(defn home-page []
  "PIK Logistic API Server")

(defn queries []
  {:result []})

(defn commands []
  {:result []})

(defn ping []
  {:result "pong"})

(defroutes home-routes
  (GET "/" [] (ok (home-page)))
  (GET "/ping" [] (ok (ping)))
  (GET "/queries" [] (ok (queries)))
  (GET "/commands" [] (ok (commands))))
