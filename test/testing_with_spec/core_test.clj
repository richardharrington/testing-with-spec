(ns testing-with-spec.core-test
  (:require [testing-with-spec.core :refer :all]
            [clojure.test :as t]
            [clojure.spec :as s]
            [clojure.spec.gen :as sgen]
            [clojure.spec.test :as stest]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcgen]
            [clojure.test.check.properties :as tcprop]
            [clojure.test.check.clojure-test :as tct]
            ))

(defn conforming-to-spec [fspec-sym]
  (let [{:keys [total check-passed]}
        (stest/summarize-results (stest/check fspec-sym))]
    (= total check-passed)))

(t/deftest test-of-42
  (t/testing "function that returns 42"
    (t/is (= (return-42) 42))))


#_(tct/defspec first-element-is-min-after-sorting             ;; the name of the test
             10000                                            ;; the number of iterations for test.check to test
             (tcprop/for-all
               [v (tcgen/not-empty (tcgen/vector tcgen/int))]
               (= (apply min v)
                  (first (sort v)))))

(t/deftest double-it-with-a-twist-spec-test
  (t/testing "function double-it-with-a-twist conforms to spec"
    (t/is (conforming-to-spec `double-it-with-a-twist))))



#_(stest/summarize-results (stest/check 'codebreaker/score))
