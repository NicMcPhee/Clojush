;; alarmclock.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.final_project_simondet.alarmclock
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;;Given a day of the week encoded as 0=Sun, 1=Mon, 2=Tue, ...6=Sat,
;;and a boolean indicating if we are on vacation, return a string of
;;the form "7:00" indicating when the alarm clock should ring. Weekdays,
;;the alarm should be "7:00" and on the weekend it should be "10:00". Unless
;;we are on vacation -- then on weekdays it should be "10:00" and weekends
;;it should be "off".
;;http://codingbat.com/prob/p119867


(def input-set
  [[0, true]
   [0, false]
   [1, true]
   [1, false]
   [2, true]
   [2, false]
   [3, true]
   [3, false]
   [4, true]
   [4, false]
   [5, true]
   [5, false]
   [6, true]
   [6, false]])

(defn expected-output
  [inputs]
  (let [[weekday on-vacation] inputs]
    (cond
    (and on-vacation (or (= weekday 0) (= weekday 6))) "off"
    (= true on-vacation) "10:00"
    (or (= weekday 0) (= weekday 6)) "10:00"
    :else "7:00")))


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
; The only part of this you'd need to change is
; which stack(s) the return value(s) come from.
(defn actual-output
  [program inputs]
  (let [start-state (make-start-state inputs)
        end-state (run-push program start-state)
        top-string (top-item :string end-state)]
    top-string))

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
                "7:00" "10:00" "off"
                'in1 'in2)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
