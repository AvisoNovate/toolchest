(ns io.aviso.toolchest.control-spec

  "Tests for io.aviso.toolchest.control."

  (:use speclj.core)
  (:require [io.aviso.toolchest.control :as c]))


(describe "io.aviso.toolchest.control"

  (context "let-error"

    (it "returns the value of the body if there are no errors"

      (should= 3
        (c/let-error [x (c/return 1)
                      y (c/return 2)]
          (+ x y))))

    (it "returns the first error that occurs"

      (should= :foo
        (c/let-error [x (c/error :foo)
                      y (c/return 2)]
          (+ x y)))

      (should= :bar
        (c/let-error [x (c/return 1)
                      y (c/error :bar)]
          (+ x y)))

      (should= :foo
        (c/let-error [x (c/error :foo)
                      y (c/error :bar)]
          (+ x y))))

    (it "evaluates init expressions before the first error, but not after"

      (should= [:foo]
        (let [state (atom [])]
          (c/let-error [x (c/return (do (swap! state conj :foo) 1))
                        _ (c/error :stop-here)
                        y (c/return (do (swap! state conj :bar) 2))]
            (+ x y))
          @state)))

    (it "does not evaluate the body expression if errors occur"

      (should-be-nil
        (let [state (atom nil)]
          (c/let-error [x (c/return 1)
                        _ (c/error :stop-here)
                        y (c/return 2)]
            (reset! state (+ x y)))
          @state))))

  (context "some-error"

    (it "returns an error when given a non-nil value"

      (should= :foo
        (c/let-error [x (c/return 1)
                      _ (c/some-error :foo)
                      y (c/return 2)]
          (+ x y))))

    (it "returns nil as a non-error value when given nil"

      (should= 3
        (c/let-error [x (c/return 1)
                      _ (c/some-error nil)
                      y (c/return 2)]
          (+ x y)))))

  )
