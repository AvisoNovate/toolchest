(ns io.aviso.toolchest.metadata
  "Functions for operating on metadata."
  {:added "0.1.2"})

(defn assoc-meta
  "Uses vary-meta to return a new object with additional metadata associated."
  [o & keyvals]
  (apply vary-meta o assoc keyvals))
