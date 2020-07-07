(ns {{name}}.s3
    (:require [cognitect.aws.client.api :as aws]
              [{{name}}.config :as cfg]
              [clojure.java.io :as io]))

(defn put-data-dump [client config filename data]
  (let [bucket    (->> config :s3 :bucket)
        directory (->> config :s3 :upload-base-dir)
        fullpath  (str bucket "/" directory)
        resp      (with-open [dump-input-stream (io/input-stream (.getBytes data))]
                    (aws/invoke client {:op      :PutObject
                                        :request {:Key    filename
                                                  :Body   dump-input-stream
                                                  :Bucket fullpath}}))
        meta      {:filename filename
                   :filepath fullpath}]
    (if-not (empty? resp)
      (merge resp meta)
      (merge {:error/code        401
              :error/message     "Dumping data fail."
              :error/s3-response resp}
             meta))))
