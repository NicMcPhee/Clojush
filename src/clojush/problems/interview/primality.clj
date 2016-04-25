;; primality.clj
;; Seeing if Clojush can "get a job" by solving Primality,
;; a problem for Clojush that is a popular interview question.
;; Jacob Opdahl, opdah023@morris.umn.edu
;; Mark Lehet, lehet005@morris.umn.edu
;; Maggie Casale, casal033@morris.umn.edu

(ns clojush.problems.interview.primality
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

(require '[clojure.math.numeric-tower :as math])

(def input-set
  "Our test case inputs."
  [
    [1]
    [2]
    [3]
    [5]
    [7]
    [11]
    [13]
    [17]
    [19]
    [23]
    [29]
    [31]
    [37]
    [41]
    [43]
    [47]
    [53]
    [59]
    [61]
    [67]
    [4]
    [6]
    [8]
    [9]
    [10]
    [12]
    [14]
    [15]
    [16]
    [18]
    [20]
    [21]
    [22]
    [24]
    [25]
    [26]
    [27]
    [28]
    [10000000]
    [69]
    ])

;; Prime function taken from:
; http://swizec.com/blog/checking-for-primes-dumber-algorithm-is-faster-algorithm/swizec/1580
(defn expected-output
  [inputs]
  (let [[n] inputs]
    (if (and (even? n) (not= n 2)) false
      (let [root (num (int (Math/sqrt n)))]
        (loop [i 3]
          (if (> i root) true
            (if (zero? (mod n i)) false
              (recur (+ i 2)))))))))

; Make a new push state, and then add every
; input to the special `:input` stack.
; You shouldn't have to change this.
(defn make-start-state
  [inputs]
  (reduce (fn [state input]
            (push-item input :input state))
          (make-push-state)
          inputs))


; We want the return value to come from the
; boolean stack.
(defn actual-output
  [program inputs]
  (let [start-state (make-start-state inputs)
        end-state (run-push program start-state)
        top-bool (top-item :boolean end-state)]
    top-bool))


; A simple error function. If the expected is actual,
; 0 error. Otherwise, it has an error of 1.
(defn all-errors
  [program]
  (doall
    (for [inputs input-set]
      (let [expected (expected-output inputs)
            actual (actual-output program inputs)]
        (if (= expected actual)
          0
          1)))))


; Here is where we choose what additional inputs and
; constants we want the run to have available to it.
(def atom-generators
  (concat (registered-for-stacks [:integer :boolean :exec])
          (list (fn [] (lrand-int 100))
                'in1)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
