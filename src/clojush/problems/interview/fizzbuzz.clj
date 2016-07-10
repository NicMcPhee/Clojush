;; fizzbuzz.clj
;; Seeing if Clojush can "get a job" by solving FizzBuzz,
;; a problem for Clojush that is a popular interview question.
;; Jacob Opdahl, opdah023@morris.umn.edu
;; Mark Lehet, lehet005@morris.umn.edu
;; Maggie Casale, casal033@morris.umn.edu

(ns clojush.problems.interview.fizzbuzz
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))


;;;;;;;;;;;;;
;; Takes an integer that will be checked for divisbility
;; by three and five
(def input-set
  "Our test case inputs."
  [[3]
   [6]
   [9]
   [12]
   [5]
   [10]
   [15]
   [18]
   [21]
   [24]
   [27]
   [20]
   [25]
   [30]
   [99]
   [69]
   [125]
   [100]
   [150]
   [225]
   [210]
   [1]
   [2]
   [4]
   [7]
   [11]
   [13]
   [14]
   [16]
   [17]
   [22]
   [28]
   [29]
   [101]
   [1007]
   [511]
   [613]
   [704]
   [89]
   [91]
   [707]
   [623]
   ]
  )


; We solve the FizzBuzz problem ourselves here.
(defn expected-output
  [inputs]
    (let [[x] inputs]
      (if (= (mod x 15) 0)
        "FizzBuzz"
        (if (= (mod x 3) 0)
          "Fizz"
          (if (= (mod x 5) 0)
            "Buzz"
            (str x))))))


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
; string stack.
(defn actual-output
  [program inputs]
  (let [start-state (make-start-state inputs)
        end-state (run-push program start-state)
        top-str (top-item :string end-state)]
    top-str))


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
  (concat (registered-for-stacks [:integer :boolean :string :exec])
          (list (fn [] (lrand-int 100))
                "Fizz" "Buzz"
                'in1)))


(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
