(ns io.aviso.toolchest.channels
  (:require [clojure.core.async :as async]))

(defn timed-out?
  "Checks if timeout-ch is closed. ATTN: if applied to a non-timeout
  channel, will consume and discard a single value from the channel's
  buffer if there is one."
  [timeout-ch]
  (let [sentinel-ch (doto (async/chan 1)
                      (async/>!! :sentinel))
        [_ ch] (async/alts!! [timeout-ch sentinel-ch] :priority true)]
    (identical? ch timeout-ch)))
