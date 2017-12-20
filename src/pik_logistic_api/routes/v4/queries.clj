(ns pik-logistic-api.routes.v4.queries
  (:require [compojure.api.sweet :refer [defroutes GET POST]]
            [ring.util.http-response :refer [ok]]
            [schema.core :as s]
            [clj-time.core :as t]
            [clj-time.format :as tf]
            [pik-logistic-api.db.core :as db]))


(def query-date-formatter (tf/formatter "yyyy-MM-dd"))


(defn date? [d]
  (try
    (do
      (tf/parse query-date-formatter d)
      true)
    (catch Exception ex false)))

;(date? "2017-11-30")
;(s/check [(s/one s/Str "at least one") s/Str] ["qwe"])


(s/defschema idle-query
  {:date_from (s/pred date?)
   :date_to   (s/pred date?)
   :zones     [(s/one s/Str "at least one") s/Str]
   :groups    [(s/one s/Str "at least one") s/Str]})


(defroutes query-routes
  (GET "/trackers" [] (ok {:result (db/get-trackers-info-by-etl)}))
  (GET "/zones" [] (ok {:result (db/get-zones)}))
  (GET "/groups" [] (ok {:result (db/get-groups)}))

  (POST "/idlebygeo" []
    :body [q idle-query]
    (ok {:result (db/get-idle-by-geo q)}))

  (POST "/idlebygroup" []
    :body [q idle-query]
    (ok {:result (db/get-idle-by-group q)}))

  (POST "/idlebygeoandgroup" []
    :body [q idle-query]
    (ok {:result (db/get-idle-by-geo-and-group q)})))
