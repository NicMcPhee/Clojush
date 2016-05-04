;; blackjack.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.coding_bat_problems.blackjack
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;;Given 2 int values greater than 0, return whichever value
;;is nearest to 21 without going over. Return 0 if they both go over.
;;http://codingbat.com/prob/p117019

(def input-set
  [[0,0]
   [0,1]
   [15,15]
   [21,20]
   [19,19]
   [22,23]
   [1000,1000]
   [18,19]
   [21,21]])

(defn expected-output
  [inputs]
  (let [[num1 num2] inputs]
    (cond
    (and (> num1 21) (> num2 21)) 0
    (> num1 21) num2
    (> num2 21) num1
    :else (max num1 num2))))

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
        top-int (top-item :integer end-state)]
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
                21 0
                'in1 'in2)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
