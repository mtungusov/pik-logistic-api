(ns pik-logistic-api.db.core
  (:require [mount.core :refer [defstate]]
            [clj-time.core :as t]
            [clj-time.format :as tf]
            [clj-time.coerce :as tc]
            [pik-logistic-api.config :refer [navyxy-time-format settings]]
            [pik-logistic-api.db.queries :as q]))

(def local-tz (t/time-zone-for-id (get-in settings [:local-tz])))

(def my-time-formatter (tf/formatter navyxy-time-format local-tz))

;(def t1 (tf/parse my-time-formatter "2017-11-27 11:53:00"))
;(tc/to-long t1)


(defstate db
          :start
          {:subprotocol (get-in settings [:sql :subprotocol])
           :subname (get-in settings [:sql :subname])
           :user (get-in settings [:sql :user])
           :password (get-in settings [:sql :password])
           :domain (get-in settings [:sql :domain])})


(defn- time-to-utc [time-str]
  (tc/to-long (tf/parse my-time-formatter time-str)))


(defn- format-tracker-info [item]
  (cond-> item
    (:time_out item) (assoc :time_out (time-to-utc (:time_out item)))
    (:time_in item) (assoc :time_in (time-to-utc (:time_in item)))
    (:gps_updated item) (assoc :gps_updated (time-to-utc (:gps_updated item)))))


(defn get-trackers-info-by-etl []
  (map format-tracker-info (q/trackers-info-by-etl db)))

;(get-trackers-info-by-etl)


(defn get-groups []
  (map :title (q/groups db)))

;(get-groups)


(defn get-zones []
  (map :label (q/zones db)))

;(get-zones)

;(def q1 {:date_from "2017-01-01" :date_to "2017-11-30"
;         :zones ["480 КЖИ - погр." "ДСК-Град - погр."]
;         :groups ["Борт 12т.-20т." "-Инлоудер"]})

;(q/idle-by-geo db q1)
;(q/idle-by-group db q1)
;(q/idle-by-geo-and-group db q1)


(defn get-idle-by-geo [params]
  (q/idle-by-geo db params))


(defn get-idle-by-group [params]
  (q/idle-by-group db params))


(defn get-idle-by-geo-and-group [params]
  (q/idle-by-geo-and-group db params))
