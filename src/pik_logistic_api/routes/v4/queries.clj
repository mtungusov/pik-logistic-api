(ns pik-logistic-api.routes.v4.queries
  (:require [compojure.api.sweet :refer [defroutes GET]]
    [ring.util.http-response :refer [ok]]
    [pik-logistic-api.db.core :as db]))

(defroutes query-routes
  (GET "/trackers" [] (ok {:result (db/get-trackers-info-by-etl)}))
  (GET "/zones" [] (ok {:result (db/get-zones)}))
  (GET "/groups" [] (ok {:result (db/get-groups)})))
