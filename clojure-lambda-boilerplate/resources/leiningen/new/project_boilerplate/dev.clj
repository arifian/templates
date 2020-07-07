(ns dev
  (:require [clojure.tools.namespace.repl :as ns-repl]
            [{{name}}.core :as core]))

(defn reload []
  (clojure.tools.namespace.repl/refresh))
