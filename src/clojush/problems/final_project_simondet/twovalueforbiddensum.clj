;; twovalueforbiddensum.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.final_project_simondet.twovalueforbiddensum
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;; Given 2 ints, a and b, return their sum. However,
;;sums in the range 10..19 inclusive, are forbidden,
;;so in that case just return 20.
;;http://codingbat.com/prob/p183071


(def input-set
  [[10, 9]
   [19, -2]
   [20, 20]
   [8, 7]
   [6, 4]
   [3, 6]
   [1, 9]
   [2, 8]
   [10, 10]])

(defn expected-output
  [inputs]
  (let [[num1 num2] inputs result (+ num1 num2)]
    (cond
    (and (> result 9) (< result 20)) 20
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
  (concat (registered-for-stacks [:integer :boolean :exec])
          (list (fn [] (lrand-int 100))
                10 19
                'in1 'in2)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
