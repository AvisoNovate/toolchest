(ns meta-spec
  "Tests for io.aviso.toolchest.metadata"
  (:use speclj.core
        io.aviso.toolchest.metadata))

(describe "io.aviso.toolchest.metadata"

  (context "copying-meta"
    (it "copies meta from the source to the result"
        (let [start (with-meta [1 2 3] {:my-meta 1})
              result (copying-meta start
                                   (map #(* 10 %) start))]

          (should= [10 20 30] result)
          (should= {:my-meta 1}
                   (meta result))))

    (it "merges expression metadata onto the source metadata"
        (let [start (with-meta [1 2 3] {:foo 1 :bar 2})
              result (copying-meta start
                                   (with-meta
                                     (map #(* 10 %) start)
                                     {:foo 10 :bazz 20}))]

          (should= [10 20 30] result)
          (should= {:foo 10 :bar 2 :bazz 20}
                   (meta result))))))

(run-specs)