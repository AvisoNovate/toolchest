(ns io.aviso.toolchest.control

  "<EXPERIMENTAL>

  Lightweight monadic control flow utilities.")


(defmacro let-error
  "<EXPERIMENTAL>

  Like let, but each init expression is destructured as

    [value error]

  If error is non-nil, it is immediately returned, otherwise value is assigned
  to the local.

  Example:

    (let-error [x (foo 1 2 3)
                y (bar 4 5 6)]
      (+ x y))

  This returns :error without calling bar if (foo 1 2 3) returns [nil :error]
  and 3 if the foo call returns [1] and the bar call returns [2]."
  [bindings & body]
  (assert (vector? bindings) "bindings must be passed in in a vector")
  (assert (even? (count bindings))
          "the bindings vector must hold an even number of items")
  (if (>= (count bindings) 2)
    (let [bname (first bindings)
          binit (fnext bindings)]
      `(let [[~bname error#] ~binit]
         (if (some? error#)
           error#
           (let-error ~(subvec bindings 2) ~@body))))
    `(do ~@body)))


(defn return
  "<EXPERIMENTAL>

  Wraps a non-error value for use in let-error (i.e. returns [value])."
  [value]
  [value])


(defn error
  "<EXPERIMENTAL>

  Wraps an error value for use in let-error (i.e. returns [nil error-value])."
  [error-value]
  [nil error-value])


(defn some-error
  "<EXPERIMENTAL>

  Helper enabling the use of functions that return an error representation or
  nil (to signify the absence of errors). Passes through nils, wraps any other
  value in [nil <the-value>]."
  [maybe-error]
  (if (some? maybe-error)
    [nil maybe-error]))
