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
(defn expected-output
  [inputs]
    (let [[in] inputs]
      (if (= in (s/reverse in))
        true
        false)))
