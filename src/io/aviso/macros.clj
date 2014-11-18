(ns io.aviso.macros
  "Generally useful macros for prettier and more expressive code.")

(defmacro cond-let
  "A merging of cond and let.  Each term is either a vector
  (in which case, it acts like let) or a condition expression followed by the value
  for that expression. An empty cond-let returns nil.

  cond-let makes it possible to create readable code that doesn't end up nesting
  endlessly."
  [& forms]
  (when forms
    (if (vector? (first forms))
      `(let ~(first forms)
         (cond-let ~@(rest forms)))
      (if-not (next forms)
        (throw (IllegalArgumentException. "cond-let requires a result form to follow each test form"))
        `(if ~(first forms)
           ~(second forms)
           (cond-let ~@(drop 2 forms)))))))

(defmacro consume
  "Consume is used to break apart a collection into individual terms.

  The format is:

      (consume coll [symbol predicate arity ...] body)

  The symbol is assigned a value by extracting zero or more values from the collection that
  match the predicate. The predicate is passed a single element from the
  collection and should return a truth value.

  The arity is a keyword that identifies how many values are taken
  from the collection.

  :one
  : The first value in the collection must match the predicate, or an exception is thrown.
    The value is assigned to the symbol.  The literal value 1 may be used instead of :one.

  :?
  : Matches 0 or 1 values from the collection. If the first value does not match the predicate,
    then nil is assigned to the symbol.

  :*
  : Zero or more values from the collection are assigned to the symbol. The symbol may be assigned
    an empty collection.

  :+
  : Matches one or more values; an exception is thrown if there are no matches.

  The symbol/pred/arity triplet can be followed by additional triplets.

  Although the above description discusses triplets, there are two special predicate values
  that are used as just a pair (symbol followed by special predicate), with no arity.

  :&
  : Used to indicate consumption of all remaining values, if any, from the collection.
    It is not followed by an arity, and must be the final term in the bindings vector.

  :+
  : Used to consume a single value always (throwing an exception if the collection is
    empty).

  consume expands into a let form, so the symbol in each triplet may be a destructuring form.

  As an example, a function that take an optional map followed at least
  one string, followed by any number of vectors:


      (defn example
        {:arglists '([options strings... & vectors] [strings... & values])}
        [& args]
        (consume args
          [options map? :?       ; nil or a map
           strings string? :+    ; seq of strings, at least one
           values :&]            ; seq of values, may be empty
           ;; Use options, strings, values here
           ...))
  "
  [coll bindings & body]
  (let [[symbol pred arity] bindings]
    (cond-let
      [binding-count (count bindings)]

      (zero? binding-count)
      `(do ~@body)

      (= pred :&)
      (if-not (= 2 binding-count)
        (throw (ex-info "Expected just symbol and :& placeholder as last consume binding."
                        {:symbol   symbol
                         :pred     pred
                         :bindings bindings}))
        `(let [~symbol ~coll] ~@body))

      (= pred :+)
      `(let [coll# ~coll]
         (if (empty? coll#)
           (throw (ex-info "consume :+ predicate on empty collection"
                           {:symbol (quote ~symbol)}))
           (let [~symbol (first coll#)]
             (consume (rest coll#) ~(drop 2 bindings) ~@body))))

      (< binding-count 3)
      (throw (ex-info "Incorrect number of binding terms for consume."
                      {:bindings bindings}))

      [remaining-bindings (drop 3 bindings)]

      :else
      (case arity

        (1 :one)
        `(let [coll# ~coll
               first# (first coll#)]
           (if-not (~pred first#)
             (throw (ex-info "consume :one arity did not match"
                             {:symbol (quote ~symbol)
                              :pred   (quote ~pred)}))
             (let [~symbol first#]
               (consume (rest coll#) ~remaining-bindings ~@body))))

        :?
        `(let [coll# ~coll
               first# (first coll#)
               match# (~pred first#)]
           (let [~symbol (if match# first# nil)]
             (consume (if match# (rest coll#) coll#) ~remaining-bindings ~@body)))

        :*
        `(let [[~symbol remaining#] (split-with ~pred ~coll)]
           (consume remaining# ~remaining-bindings ~@body))

        :+
        `(let [[matching# remaining#] (split-with ~pred ~coll)]
           (if (empty? matching#)
             (throw (ex-info "Expected to consume at least one match with :+ arity"
                             {:symbol (quote ~symbol)
                              :pred   (quote ~pred)})))
           (let [~symbol matching#]
             (consume remaining# ~remaining-bindings ~@body)))

        (throw (ex-info "Unknown arity in consume binding. Expected :one, :?, :*, or :+."
                        {:bindings bindings
                         :body     body}))))))