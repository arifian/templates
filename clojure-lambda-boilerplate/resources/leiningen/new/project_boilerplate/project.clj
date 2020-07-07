(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.datomic/client-pro "0.8.28"]
                 [org.clojure/data.json "0.2.6"]
                 [clojure.java-time "0.3.2"]
                 [com.cognitect.aws/api "0.8.301"]
                 ;; [com.cognitect.aws/api "0.8.301" :exclusions [org.eclipse.jetty/jetty-client
                 ;;                                               org.eclipse.jetty/jetty-io
                 ;;                                               org.eclipse.jetty/jetty-http
                 ;;                                               org.eclipse.jetty/jetty-util]]
                 [com.cognitect.aws/endpoints "1.1.11.537"]
                 [com.cognitect.aws/s3 "714.2.430.0"]
                 [com.cognitect.aws/ssm "757.2.543.0"]]
  :resource-paths ["config"]
  :profiles {:repl    {:source-paths   ["dev" "src" "src_net"]
                       :resource-paths ["config" "resources" "test/resources"]
                       :dependencies   [[org.clojure/tools.namespace "0.2.10"]
                                        [io.pedestal/pedestal.service-tools "0.5.5"]]}
             :uberjar {:jvm-opts ["-Dclojure.compiler.direct-linking=true"]
                       :aot      [{{name}}.core]}}
  :main ^{:skip-aot true} {{name}}.core
  :repl-options {:init-ns user}
  ;; :aot :all
  )
