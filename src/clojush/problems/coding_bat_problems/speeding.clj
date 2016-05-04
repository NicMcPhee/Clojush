;; speeding.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.final_project_simondet.speeding
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
  [[55, false]
   [60, false]
   [65, false]
   [70, false]
   [75, false]
   [80, false]
   [85, false]
   [90, false]
   [55, true]
   [60, true]
   [65, true]
   [70, true]
   [75, true]
   [80, true]
   [85, true]
   [90, true]])

(defn expected-output
  [inputs]
  (let [[speed birthday] inputs]
    (cond
    (and (not birthday) (< speed 61)) 0
    (and birthday (< speed 66)) 0
    (and birthday (< speed 86)) 1
    (and (not birthday) (< speed 81)) 1
    :else 2)))


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
                1 2 0 60 65 80 85
                'in1 'in2)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
