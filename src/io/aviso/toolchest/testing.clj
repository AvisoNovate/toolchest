(ns io.aviso.toolchest.testing
  "Tools for writing tests."
  {:added "0.1.3"})

(defmacro is-thrown
  "Expects the expression to thrown an exception.

  If the expression does throw, then the exception
  is caught, bound to the symbol, and the body is evaluated.

  The body can then perform tests on the details of the exceptions.

  Otherwise, if no exception is thrown, then a new exception *is* thrown,
  detailing the expression that was expected to fail.

  sym
  : symbol that we be bound to the exception thrown from the expression

  exp
  : expression to evaluate

  body
  : evaluated with sym bound

  Example:

      (is-thrown [e (parse-invalid-file \"invalid.txt\")]
        (is (= :parse/failure
            (-> e ex-data :type))))

  Evaulates to nil."
  [[sym exp] & body]
  `(when-not
     (try
       ~exp
       ;; Expect to throw, and it didn't!
       false
       (catch Throwable ~sym
         ~@body
         true))
     (throw (ex-info "Expression failed to thrown an exception."
                     {:expression (quote ~exp)}))))
