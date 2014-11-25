(ns io.aviso.toolchest.exceptions
  "Some assistance for dealing with exceptions."
  {:added "0.1.1"}
  (:require
    [clojure.string :as str]))

(defn to-message
  "Converts an exception to a message. Normally, this is the message property of the exception, but if
  that's blank, the fully qualified class name of the exception is used instead."
  [^Throwable t]
  (let [m (.getMessage t)]
    (if-not (str/blank? m)
      m
      (-> t .getClass .getName))))
