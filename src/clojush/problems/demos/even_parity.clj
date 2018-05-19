;; even_parity.clj
;;
;; An example implementation of the (overly) artificial boolean test
;; problem for GP.
;; Nic McPhee, 2017

(ns clojush.problems.demos.even-parity
  (:use [clojush.pushgp.pushgp]
        [clojush.pushstate]
        [clojush.random]
        [clojush.interpreter])
  (:require [clojure.math.combinatorics :as combo]))

;;;;;;;;;;;;
;; Boolean symbolic regression problem where the target is to
;; evolve the boolean even-parity function (returns true if an
;; even number of its inputs are true) using just AND, OR, NAND,
;; and NOR. Koza introduced this in the late 80's/early 90's and
;; it was widely used as a synthetic test problem in the 90's and
;; 00's, especially in theoretical work and work on modularity.

; I feel like this should be a command line argument rather than
; a cooked in constant.
(def *num-even-parity-bits* 5)

(defn make-start-state
  [individual input]
  ; Why are we looping to put these inputs on the
  ; stack? We should have a function that does this
  ; (maybe with concat?) and just call it.
  (loop [state (make-push-state)
         bools input]
    (if (empty? bools)
      state
      (recur (push-item (first bools) :input state)
             (rest bools)))))

(defn even-parity? [bools]
  "Returns true if the collection of booleans has an even number of true's"
  (even? (count (filter identity bools))))

(defn parity-compute-errors
  [individual]
  (let [inputs (apply combo/cartesian-product
                      (repeat *num-even-parity-bits* [true false]))]
    (doall
      (for [input inputs]
        (let [start-state (make-start-state individual input)
              end-state (run-push (:program individual) start-state)
              top-boolean (top-item :boolean end-state)]
          (if (instance? Boolean top-boolean)
            (if (= top-boolean (even-parity? input))
              0
              1)
            1000))))))

(defn error-function
  [individual]
  (assoc individual :errors (parity-compute-errors individual)))

; There's got to be a better way than listing all the input variables
; separately here.
(def argmap
  {:error-function error-function
   :atom-generators (list 'in1
                          'in2
                          'in3
                          'in4
                          'in5
                          'boolean_and
                          'boolean_or
                          'boolean_nand
                          'boolean_nor)
   :max-points 3200
   :max-genome-size-in-initial-program 400
   :evalpush-limit 1600
   :population-size 1000
   :max-generations 300
   :parent-selection :lexicase
   :genetic-operator-probabilities {:alternation 0.2
                                    :uniform-mutation 0.2
                                    :uniform-close-mutation 0.1
                                    [:alternation :uniform-mutation] 0.5}

   :alternation-rate 0.01
   :alignment-deviation 10
   :uniform-mutation-rate 0.01
   :report-simplifications 0
   :final-report-simplifications 5000
   :max-error 20000})
