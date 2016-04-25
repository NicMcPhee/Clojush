;; evenlyspaced.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.final_project_simondet.evenlyspaced
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;; Given three ints, a b c, one of them is small, one is
;;medium and one is large. Return true if the three values
;;are evenly spaced, so the difference between small and
;;medium is the same as the difference between medium and large.
;;http://codingbat.com/prob/p198700


(def input-set
  [[0,1,2]
   [2,1,0]
   [10,20,30]
   [20,30,10]
   [3,6,9]
   [1,5,29]
   [2,6,4]
   [8,16,24]
   [9,18,27]])



(defn expected-output
  [inputs]
  (let [[temperature is-summer] inputs]
    (and (>= temperature 60)
         (if is-summer
           (<= temperature 100)
           (<= temperature 90)))))

(defn test-output
  [num1 num2 num3]
  (let (max num1 num2 num3) large
    (min num1 num2 num3) small


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
