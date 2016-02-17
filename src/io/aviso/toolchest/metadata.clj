(ns io.aviso.toolchest.metadata
  "Functions for operating on metadata."
  {:added "0.1.2"})

(defn assoc-meta
  "Uses vary-meta to return a new object with additional metadata associated."
  [x & keyvals]
  (apply vary-meta x assoc keyvals))

(defmacro merging-meta
  "Evaluates the expression, then merges metadata from x into the result.
  Metadata on the expression takes precendence over metadata from x.

  This helps with clojure.core collection functions (map, reduce, etc.)
  that do not maintain metadata."
  {:added "0.1.4"}
  [x expression]
  `(let [result# ~expression]
     (with-meta result#
                (merge (meta ~x)
                       (meta result#)))))