(ns {{name}}.core
    (:require [{{name}}.config :as cfg]
              [{{name}}.clients :as clients]
              [{{name}}.datomic :as datomic]
              [{{name}}.s3 :as s3]
              [java-time :as jtime])
    (:gen-class
     :methods [^:static [handler [java.util.Map] String]]))

;; to parse json params from invoke

(defprotocol ConvertibleToClojure
  (->cljmap [o]))

(extend-protocol ConvertibleToClojure
  java.util.Map
  (->cljmap [o] (let [entries (.entrySet o)]
                  (reduce (fn [m [^String k v]]
                            (assoc m (keyword k) (->cljmap v)))
                          {} entries)))

  java.util.List
  (->cljmap [o] (vec (map ->cljmap o)))

  java.lang.Object
  (->cljmap [o] o)

  nil
  (->cljmap [_] nil))

;; main handler

;; (java.util.Time.)

(defn -handler [s]
  (let [init-config   (when (= nil @cfg/my-config) (cfg/get-config))
        init-dtm      (when (= nil @clients/my-datomic) (clients/get-datomic))
        _ (println (->cljmap s))]
    (str "helo"))) 


;; _ (println (->cljmap s))
;; __ (println "Hello World!")
