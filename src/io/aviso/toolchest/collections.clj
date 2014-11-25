(ns io.aviso.toolchest.collections
  "Some useful functions related to Clojure collections."
  {:added "0.1.1"})

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