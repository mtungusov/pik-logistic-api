(ns pik-logistic-api.config
  (:require [clojure.java.io :as io]
            [cprop.core :refer [load-config]]
            [mount.core :refer [defstate]])
  (:import [java.io File]))

(def navyxy-time-format "yyyy-MM-dd HH:mm:ss")

(defn get-path [filename]
  (->> filename
       (str "." File/separator "config" File/separator)
       (io/as-file)
       (.getAbsolutePath)))

(defstate settings
          :start
          (load-config :file (get-path "secrets.edn")))
