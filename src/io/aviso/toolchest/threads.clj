(ns io.aviso.toolchest.threads
  "Utilities associated with Clojure's threading macros (-> and ->>)."
  {:added "0.1.1"})

(defn apply->
  "Applies a function to the threaded value, using the threaded value as the first argument to the function."
  ([value f]
    (f value))
  ([value f x]
    (f value x))
  ([value f x y]
    (f value x y))
  ([value f x y & arguments]
    (apply f (apply vector value x y arguments))))

(defn apply->>
  "Applies a function to the threaded value, using the value as the last argument to the function."
  ([value f]
    (f value))
  ([value f x]
    (f x value))
  ([value f x y]
    (f x y value))
  ([value f x y & arguments]
    (apply f x y (-> arguments vec (conj value)))))



