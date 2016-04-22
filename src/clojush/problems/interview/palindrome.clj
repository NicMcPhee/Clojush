;; palindrome.clj
;; Seeing if Clojush can "get a job" by solving Palindrome,
;; a problem for Clojush that is a popular interview question.
;; Jacob Opdahl, opdah023@morris.umn.edu
;; Mark Lehet, lehet005@morris.umn.edu
;; Maggie Casale, casal033@morris.umn.edu

(ns clojush.problems.interview.palindrome
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))


; Used to reverse a string nicely
; in our expected-output function.
(require '[clojure.string :as s])


;;;;;;;;;;;;;
;; Takes a string that will be checked
;; to see if it is a palindrome.
(def input-set
  "Our test case inputs."
  [["not a palindrome"]
   ["racecar"]
   ["race car"]
   ]
  )


; We solve the Palindrome problem ourselves here.
; If the input string is a palindrome, return true.
; Else, return false.
(defn expected-output
  [inputs]
    (let [[in] inputs]
      (if (= in (s/reverse in))
        true
        false)))


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
  (concat (registered-for-stacks [:boolean :string :exec])
          (list 'in1)))


(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })


