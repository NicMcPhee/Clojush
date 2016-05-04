;; teninbetween.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.final_project_simondet.teninbetween
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;; Given three ints, a b c, return true if one of them
;;is 10 or more less than one of the others.
;;http://codingbat.com/prob/p179196


(def input-set
  [[10,1,2]
   [1,20,3]
   [1,20,2]
   [1,2,1]
   [2,30,4]
   [2,2,4]
   [2,3,2]
   [3,1,30]
   [30,3,1]
   [1,30,3]])

(defn expected-output
  [inputs]
  (let [[num1 num2 num3] inputs]
    (cond
      (<= 10 (Math/abs (- num3 num2))) true
      (<= 10 (Math/abs (- num2 num1))) true
      (<= 10 (Math/abs (- num3 num1))) true
      :else false)))

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
        top-bool (top-item :boolean end-state)]
    top-bool))

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
                10
                'in1 'in2 'in3)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
