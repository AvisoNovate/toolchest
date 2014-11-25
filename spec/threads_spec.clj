(ns threads-spec
  "Tests for io.aviso.toolchest.threads."
  (:use speclj.core
        io.aviso.toolchest.threads))

(describe "io.aviso.toolchest.threads"

  (context "apply->"

    (it "passes the threaded value to a 1-arity function"
        (->> (-> 2
                 inc
                 (apply-> str))
             (should= "3")))

    (it "passes the threaded value as the 1st argument to a 2-arity function"
        (->> (-> [2 3]
                 (apply-> #(map %2 %1) inc))
             (should= [3 4])))

    (it "passes the threaded value as the 1st argument to a 3-arity function"
        (->> (-> :a
                 (apply-> vector :b :c))
             (should= [:a :b :c])))

    (it "passes the threaded value as the 1st argument to a 4+-arity function"
        (->> (-> :a
                 (apply-> vector :b :c :d))
             (should= [:a :b :c :d]))))

  (context "apply->>"

    (it "passes the threaded value to a 1-arity function"
        (->> (-> 2
                 inc
                 (apply->> str))
             (should= "3")))

    (it "passes the threaded value as the last argument to a 2-arity function"
        (->> (-> :a
                 (apply->> vector :b))
             (should= [:b :a])))

    (it "passes the threaded value as the last argument to a 3-arity function"
        (->> (-> [1 2 3]
                 (apply->> map inc))
             (should= [2 3 4])))

    (it "passes the threaded value as the last argument to a 4+-arity function"
        (->> (-> :a
                 (apply->> vector :b :c :d))
             (should= [:b :c :d :a])))))

(run-specs)