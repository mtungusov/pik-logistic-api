(ns pik-logistic-api.db.queries
  (:require [hugsql.core :as hugsql]
            [clj-time.core :as t]
            [clj-time.format :as tf]))

(hugsql/def-db-fns "queries.sql")

(def navyixy-time-formatter (tf/formatter "yyyy-MM-dd HH:mm:ss"))
(def one-day (t/days 1))

(defn- to-sql-time [t]
  (tf/unparse navyixy-time-formatter t))

;(to-sql-time (t/minus (t/now) one-day))
;(-> (t/now) (t/minus one-day) to-sql-time)

(defn get-trackers-info
  ([conn time] (trackers-info conn {:time time}))
  ([conn] (let [time (-> (t/now) (t/minus one-day) to-sql-time)]
            (get-trackers-info conn time))))
