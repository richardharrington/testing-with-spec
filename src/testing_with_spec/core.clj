(ns testing-with-spec.core
  (:require [clojure.test :as t]
            [clojure.spec :as s]
            [clojure.spec.gen :as sgen]
            [clojure.spec.test :as stest]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcgen]
            [clojure.test.check.properties :as tcprop]

            )
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



(defn return-42 []
  42)

(s/fdef double-it-with-a-twist
        :args (s/cat :x pos-int?)
        :ret (s/and pos-int? #(<= % 200))
        :fn (s/or :gt-100  (s/and #(> (-> % :args :x) 100)
                                  #(odd? (:ret %))
                                  #(< (:ret %) (-> % :args :x)))
                  :lte-100 (s/and #(<= (-> % :args :x) 100)
                                  #(even? (:ret %))
                                  #(> (:ret %) (-> % :args :x)))))


(defn double-it-with-a-twist [x]
  (if (> x 100)
    44
    (* x 2)))

(def sort-idempotent-prop
  (tcprop/for-all [v (tcgen/vector tcgen/int)]
                  (= (sort v) (sort (sort v)))))


(def prop-sorted-first-less-than-last
  (tcprop/for-all [v (tcgen/not-empty (tcgen/vector tcgen/int))]
                  (let [s (sort v)]
                    (< (first s) (last s)))))
