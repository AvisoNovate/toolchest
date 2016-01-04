(ns user
  (:use clojure.repl
        criterium.core
        clojure.pprint
        speclj.config))

(alter-var-root #'default-config assoc :color true :reporters ["documentation"])
