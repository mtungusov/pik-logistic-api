(ns pik-logistic-api.routes.home
  (:require [compojure.api.sweet :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]))

(defroutes home-routes
  (GET "/" [] (ok "PIK Logistic API Server"))
  (GET "/ping" [] (ok {:result "pong"})))
