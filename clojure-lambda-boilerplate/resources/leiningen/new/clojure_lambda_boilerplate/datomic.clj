(ns {{name}}.datomic
    (:require [clojure.core.reducers]
              [datomic.client.api.async :as dd]
              [clojure.core.async :as async :refer (<!!)]
              [cognitect.anomalies :as anom]))

;; old forms

;; (declare merge-data xform)

;; (defn merge-data
;;   ([] [])
;;   ([out] out)
;;   ([xs x] (conj xs x)))

;; (def xform
;;   (comp (halt-when ::anom/category)
;;         cat
;;         (map first)))

;; (defn xform-with-myfn [myfn]
;;   (comp (halt-when ::anom/category)
;;         cat
;;         (map first)
;;         (map myfn)))

;; query pull

(def user-pattern
  [])

(defn pull-user-data [db entity-vector]
  (do (Thread/sleep 1) ;; throttle 1 msec for each user
      (<!! (dd/pull db {:selector user-pattern
                        :eid      (first entity-vector)}))))

(defn pull-user-entity [db]
  (->> (dd/q {:query [:find '?e ;; (conj '() ['*] '?e 'pull)
                      :where ['?e :user/id '?ident]]
              :args  [db]
              ;; :limit 10
              ;; :offset 10
              :timeout 900000
              :chunk 100000000})
       <!!))
