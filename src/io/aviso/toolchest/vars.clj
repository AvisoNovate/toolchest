(ns io.aviso.toolchest.vars
  "Functions to manipulate Vars."
  {:added "0.1.4"})

(defn reset-var!
  "Alters the root value of a Var, ignoring the current value."
  [v value]
  (alter-var-root v (constantly value)))
