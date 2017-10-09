(defproject pik-logistic-api "0.1.0-SNAPSHOT"
  :description "PIK Logistic API backend"
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [mount "0.1.11"]
                 [cprop "0.1.11"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.25"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 [com.layerware/hugsql "0.4.7"]
                 [org.clojure/java.jdbc "0.7.0"]
                 [net.sourceforge.jtds/jtds "1.3.1"]]
  :main pik-logistic-api.core
  :profiles {:uberjar {:omit-source true
                       :aot :all
                       :uberjar-name "pik-logistic-api.jar"}})