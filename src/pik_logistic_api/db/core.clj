(ns pik-logistic-api.db.core
  (:require [mount.core :refer [defstate]]
            [pik-logistic-api.config :refer [settings]]
            [pik-logistic-api.db.queries :as q]))

(defstate db
          :start
          {:subprotocol (get-in settings [:sql :subprotocol])
           :subname (get-in settings [:sql :subname])
           :user (get-in settings [:sql :user])
           :password (get-in settings [:sql :password])
           :domain (get-in settings [:sql :domain])})

;(identity db)

;(first (q/get-trackers-info db "2017-10-05 00:00:00"))

(defn get-trackers-info []
  (q/get-trackers-info db))
  ;(q/get-trackers-info db "2017-10-05 00:00:00"))

;(get-trackers-info)


(defn get-trackers-info-by-etl []
  (q/trackers-info-by-etl db))

;(get-trackers-info-by-etl)


(defn get-groups []
  (sort (map :title (q/groups db))))

;(get-groups)


(defn get-zones []
  (sort (map :label (q/zones db))))

;(get-zones)