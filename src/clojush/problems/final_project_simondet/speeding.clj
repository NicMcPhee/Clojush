;; speeding.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.ec-ai-demos.speeding
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;; You are driving a little too fast, and a police officer
;;stops you. Write code to compute the result, encoded as an
;;int value: 0=no ticket, 1=small ticket, 2=big ticket. If
;;speed is 60 or less, the result is 0. If speed is between
;;61 and 80 inclusive, the result is 1. If speed is 81 or more,
;;the result is 2. Unless it is your birthday -- on that day,
;;your speed can be 5 higher in all cases.
;;http://codingbat.com/prob/p137202

(def input-set
  [[70, false]
   [70, true]
   [95, false]
   [95, true]
   [90, false]
   [90, true]
   [50, false]
   [50, true]
   [100, false]
   [100, true]
   [105, true]
   [59, false]
   [59, true]
   [60, false]
   [59, true]
   [91, false]
   [91, true]
   [101, true]
   [101, false]])

(defn expected-output
  [inputs]
  (let [[temperature is-summer] inputs]
    (and (>= temperature 60)
         (if is-summer
           (<= temperature 100)
           (<= temperature 90)))))

; Make a new push state, and then add every
; input to the special `:input` stack.
; You shouldn't have to change this.
(defn make-start-state
  [inputs]
  (reduce (fn [state input]
            (push-item input :input state))
          (make-push-state)
          inputs))

; The only part of this you'd need to change is
; which stack(s) the return value(s) come from.
(defn actual-output
  [program inputs]
  (let [start-state (make-start-state inputs)
        end-state (run-push program start-state)
        top-int (top-item :boolean end-state)]
    top-int))

(defn all-errors
  [program]
  (doall
    (for [inputs input-set]
      (let [expected (expected-output inputs)
            actual (actual-output program inputs)]
        (if (= expected actual)
          0
          1)))))

(def atom-generators
  (concat (registered-for-stacks [:integer :boolean :exec])
          (list (fn [] (lrand-int 100))
                60 90 100
                'in1 'in2)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
