(ns {{name}}.config
    (:require
     [cognitect.aws.client.api :as aws]
     [clojure.edn :as edn]))

;; (set! *print-level* false)
;; (set! *print-length* false)

(declare params)

(def params (aws/client {:api :ssm}))

(defn get-ssm-params [env names]
  (-> {:op :GetParameters :request {:Names names
                                    :WithDecryption true}}
      (->> (aws/invoke params))
      :Parameters
      (->> (mapv #(-> % :Value clojure.edn/read-string)))
      (->> (reduce merge {:env env}))))

(def my-config (atom nil))

(defn config []
  (case (System/getenv "ENV")
    "staging" (get-ssm-params :staging ["/param-path/a"
                                        "/param-path/b"])
    
    "prod" (get-ssm-params :prod ["/param-path/a"
                                  "/param-path/b"])
    
    (->> "config/config.edn" slurp clojure.edn/read-string)))

(defn get-config []
  (when-not @my-config
    (reset! my-config (config)))
  @my-config)

#_(get-config)
