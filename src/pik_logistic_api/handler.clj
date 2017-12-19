(ns pik-logistic-api.handler
  (:require [clojure.tools.logging :as log]
            [ring.logger :as logger]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.util.http-response :as response]
            [compojure.api.sweet :refer :all]
            [compojure.route]
            [pik-logistic-api.routes.home :refer [home-routes]]
            [pik-logistic-api.routes.v4.queries :as v4]))


(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        (log/error t)
        (response/internal-server-error "Something very bad has happened!")))))


(defn wrap-my-cors [handler]
  (wrap-cors handler
             :access-control-allow-origin [#".*"]
             :access-control-allow-methods #{:get :post}
             :access-control-allow-credentials true))


(def api-routes
  (api
    (context "/api/v4" []
      #'home-routes
      (context "/q" []
        #'v4/query-routes))
    (undocumented
      (compojure.route/not-found (response/not-found "page not found")))))


(defn app []
  (-> #'api-routes
      wrap-internal-error
      wrap-my-cors
      logger/wrap-with-logger))
