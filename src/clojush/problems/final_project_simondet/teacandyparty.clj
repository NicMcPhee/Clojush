;; teacandyparty.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Ben Simondet, simon998@morris.umn.edu, 2016

(ns clojush.problems.ec-ai-demos.teacandyparty
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;;We are having a party with amounts of tea and candy.
;;Return the int outcome of the party encoded as 0=bad,
;;1=good, or 2=great. A party is good (1) if both tea and
;;candy are at least 5. However, if either tea or candy is
;;at least double the amount of the other one, the party is
;;great (2). However, in all cases, if either tea or candy
;;is less than 5, the party is always bad (0).
;;http://codingbat.com/prob/p177181


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
