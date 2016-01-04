(ns collections-spec
  (:use speclj.core
        io.aviso.toolchest.collections))

(describe "io.aviso.toolchest.collections/update-if?"
  (it "should return map unchanged if key not present"
      (let [m {:foo :bar}]
        (should-be-same m
                        (update-if? m :baz inc))))

  (it "should apply the function to the value if present"
      (let [m {:val 10}]
        (should= 15 (-> m
                        (update-if? :val + 5)
                        :val)))))

(run-specs)
