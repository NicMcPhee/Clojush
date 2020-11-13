;; threevaluesum.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.coding_bat_problems.threevaluesum
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;; Given 3 int values, a b c, return their sum. However,
;;if one of the values is the same as another of the values,
;;it does not count towards the sum.
;;http://codingbat.com/prob/p143951

(def input-set
  [[1,1,2]
   [1,2,3]
   [1,2,2]
   [1,2,1]
   [2,3,4]
   [2,2,4]
   [2,3,2]
   [3,1,3]
   [3,3,1]
   [1,3,3]])

(defn expected-output
  [inputs]
  (let [[num1 num2 num3] inputs result (+ num1 num2 num3)]
    (cond
      (= num1 num2) (+ num1 num3)
      (= num2 num3) (+ num1 num2)
      (= num1 num3) (+ num2 num3)
      :else result)))

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
  (concat (registered-for-stacks [:integer :boolean])
          (remove #{'exec_y} (registered-for-stacks [:exec]))
          (list (fn [] (lrand-int 100))
                'in1 'in2 'in3)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
