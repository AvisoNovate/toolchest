(ns collections-spec
  (:use speclj.core
        io.aviso.toolchest.collections))

(describe "io.aviso.toolchest.collections"

  (describe "into+"
    (it "returns a single collection unchanged"
        (let [c [:a]]
          (should-be-same c
                          (into+ c))))

    (it "should combine multiple collections"
        (should= [1 2 3 4 5 6 7 8 9]
                 (into+ [1] [2 3] (range 4 10)))))

  (describe "update-if?"
    (it "should return map unchanged if key not present"
        (let [m {:foo :bar}]
          (should-be-same m
                          (update-if? m :baz inc))))

    (it "should apply the function to the value if present"
        (let [m {:val 10}]
          (should= 15 (-> m
                          (update-if? :val + 5)
                          :val))))))

(run-specs)
