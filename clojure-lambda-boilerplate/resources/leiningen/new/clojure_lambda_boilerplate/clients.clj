(ns {{name}}.clients
    (:require [datomic.client.api.async :as dd]
              [clojure.core.async :as async :refer (<!!)]
              [cognitect.aws.client.api :as aws]
              [{{name}}.config :as cfg]))

;; s3

(declare s3)

(def s3 (aws/client {:api :s3}))

;; datomic

(def client-async (dd/client (->> (cfg/get-config) :datomic-client (#(select-keys % [:server-type
                                                                                     :access-key
                                                                                     :secret
                                                                                     :endpoint])))))
(defn conn-async [] (<!! (dd/connect client-async {:db-name (->> (cfg/get-config) :datomic-client :db-name)})))
(defn a-db [] (dd/db (conn-async)))

;; init dtm

(def my-datomic (atom nil))

(defn get-datomic []
  (when-not @my-datomic
    (let [conn (conn-async)]
      (reset! my-datomic {:conn conn
                          :db   (dd/db conn)})))
  @my-datomic)

(defn get-datomic-now-db [{:keys [db conn] :as datomic}]
  (assoc datomic
         :db (dd/db conn)))
