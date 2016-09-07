(defproject io.aviso/toolchest "0.1.6-SNAPSHOT"
  :description "Generally useful macros and functions"
  :url "https://github.com/AvisoNovate/toolchest"
  :license {:name "Apache Sofware License 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :profiles {:dev
             {:dependencies [[speclj "3.3.1"]
                             [criterium "0.4.3"]]}}
  ;; List "resolved" dependencies first, which occur when there are conflicts.
  ;; We pin down the version we want, then exclude anyone who disagrees.
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :plugins [[speclj "3.3.1"]
            [lein-codox "0.9.3"]]
  :aliases {"release" ["do"
                       "clean,"
                       "spec,",
                       "codox,"
                       "deploy" "clojars"]}
  :test-paths ["spec"]
  :codox {:source-uri "https://github.com/AvisoNovate/toolchest/blob/master/{filepath}#L{line}"
          :metadata   {:doc/format :markdown}})
