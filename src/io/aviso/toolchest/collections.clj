(ns io.aviso.toolchest.collections
  "Some useful functions related to Clojure collections."
  {:added "0.1.1"}
  (:require [clojure.pprint :as pprint]))

(defn hash-map-by
  "Constructs a hash map from the supplied values.

  key-fn
  : Passed a value and extracts the key for that value.

  value-fn
  : Passed a value and converts it to be a map value. If omitted, then the values
    provided are used as-is in the output map.

  values
  : Seq of values from which map keys and values will be extracted."
  ([key-fn values]
    (zipmap (map key-fn values) values))
  ([key-fn value-fn values]
    (zipmap (map key-fn values) (map value-fn values))))

(defn pretty-print
  "Pretty-prints the supplied object to a returned string."
  [object]
  (pprint/write object
                :stream nil
                :pretty true))

(def ^:dynamic *default-brief-level*
  "Default for [[pretty-print-brief]] when setting the maximium print level."
  4)

(def ^:dynamic *default-brief-length*
  "Default for [[pretty-print-brief]] when setting the maximum print length."
  5)

(defn pretty-print-brief
  "Like [[pretty-print]], but prints the object more briefly, with limits on level and length.

  Returns the printed object as a string."
  ([object]
    (pretty-print-brief object *default-brief-level* *default-brief-length*))
  ([object print-level print-length]
    (binding [*print-level* print-level
              *print-length* print-length]
      (pretty-print object))))