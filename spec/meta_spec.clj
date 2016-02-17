(ns meta-spec
  "Tests for io.aviso.toolchest.metadata"
  (:use speclj.core
        io.aviso.toolchest.metadata))

(describe "io.aviso.toolchest.metadata"

  (context "merging-meta"
    (it "merges expression metadata onto the source metadata"
        (let [start (with-meta [1 2 3] {:foo 1 :bar 2})
              result (merging-meta start
                                   (with-meta
                                     (map #(* 10 %) start)
                                     {:foo 10 :bazz 20}))]

          (should= [10 20 30] result)
          (should= {:foo 10 :bar 2 :bazz 20}
                   (meta result))))))

(run-specs)