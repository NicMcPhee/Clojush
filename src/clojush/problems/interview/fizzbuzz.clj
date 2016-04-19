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
  [3
   6
   9
   12
   5
   10
   15
   18
   21
   24
   27
   20
   25
   30
   99
   69
   125
   100
   150
   225
   210
   1
   2
   4
   7
   11
   13
   14
   16
   17
   22
   28
   29
   101
   1007
   511
   613
   704
   89
   91
   707
   623
   ]
  )

(defn expected-output
  [input]
    (if (== (mod input 15) 0)
      "FizzBuzz"
      (if (== (mod input 3) 0)
        "Fizz"
        (if (== (mod input 5) 0)
          "Buzz"
          ))))

