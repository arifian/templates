(ns leiningen.new.clojure-lambda-boilerplate
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize-ns multi-segment]]
            [leiningen.core.main :as main]))

(def render-project (renderer "project-boilerplate"))

(def render-source (renderer "clojure-lambda-boilerplate"))

(defn clojure-lambda-boilerplate
  "FIXME: write documentation"
  [name]
  (let [data {:name      name
              :sanitized (name-to-path name)
              :namespace (multi-segment (sanitize-ns name))}]
    (main/info "Generating fresh 'lein new' clojure-lambda-boilerplate project.")
    (->files data
             ["project.clj" (render-project "project.clj" data)]
             ["README.md" (render-project "README.md" data)]
             ["deploy-to-prod.sh" (render-project "deploy-to-prod.sh" data)]
             ["deploy-to-staging.sh" (render-project "deploy-to-staging.sh" data)]
             ["config/config.edn" (render-project "config.edn" data)]
             ["config/logback.xml" (render-project "logback.xml" data)]
             ["dev/dev.clj" (render-project "dev.clj" data)]
             ["dev/user.clj" (render-project "user.clj" data)]
             [".gitignore" (render-project ".gitignore" data)]
             
             ["src/{{sanitized}}/clients.clj" (render-source "clients.clj" data)]
             ["src/{{sanitized}}/config.clj" (render-source "config.clj" data)]
             ["src/{{sanitized}}/core.clj" (render-source "core.clj" data)]
             ["src/{{sanitized}}/datomic.clj" (render-source "datomic.clj" data)]
             ["src/{{sanitized}}/s3.clj" (render-source "s3.clj" data)])))
