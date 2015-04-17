(ns user
  (:use clojure.repl
        io.aviso.repl
        criterium.core
        clojure.pprint
        speclj.config))

(install-pretty-exceptions)

(alter-var-root #'default-config assoc :color true :reporters ["documentation"])
