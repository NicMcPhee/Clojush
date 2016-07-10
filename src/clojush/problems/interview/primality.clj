;; primality.clj
;; Seeing if Clojush can "get a job" by solving Primality,
;; a problem for Clojush that is a popular interview question.
;; Jacob Opdahl, opdah023@morris.umn.edu
;; Mark Lehet, lehet005@morris.umn.edu
;; Maggie Casale, casal033@morris.umn.edu

(ns clojush.problems.interview.primality
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))


; Used in the function we borrowed to determine primality
; for our expected-output function.
(require '[clojure.math.numeric-tower :as math])

;;;;;;;;;;;;;
;; Takes an integer that will be checked
;; to see if it is prime.
(def input-set
  "Our test case inputs."
  [[2]
   [3]
   [5]
   [7]
   [11]
   [13]
   [17]
   [19]
   [23]
   [29]
   [31]
   [37]
   [41]
   [43]
   [47]
   [53]
   [59]
   [61]
   [67]
   [71]
   [73]
   [79]
   [83]
   [89]
   [97]
   [101]
   [103]
   [107]
   [109]
   [113]
   [127]
   [131]
   [137]
   [139]
   [149]
   [151]
   [157]
   [163]
   [167]
   [173]
   [179]
   [181]
   [191]
   [193]
   [197]
   [199]
   [211]
   [223]
   [227]
   [2011]
   [2749]
   [3041]
   [3769]
   [4463]
   [4513]
   [405239]
   [407207]
   [774523]
   [809929]
   [973129]
   [977609]
   [992219]
   [994913]
   [996637]
   [997123]
   [997153]
   [101663]
   [865061]
   [873157]
   [6907]
   [101359]
   [102203]
   [103237]
   [110563]
   [109507]
   [117679]
   [117751]
   [119503]
   [119417]
   [123493]
   [984959]
   [983317]
   [986257]
   [462421]
   [461917]
   [467633]
   [13001]
   [12203]
   [14771]
   [17291]
   [21001]
   [25013]
   [27127]
   [29389]
   [15737]
   [16603]
   [44963]
   [16187]
   [16903]
   [4]
   [6]
   [8]
   [9]
   [10]
   [12]
   [14]
   [15]
   [16]
   [18]
   [20]
   [21]
   [22]
   [24]
   [25]
   [26]
   [27]
   [28]
   [30]
   [32]
   [33]
   [34]
   [35]
   [36]
   [38]
   [39]
   [40]
   [42]
   [44]
   [45]
   [46]
   [48]
   [49]
   [50]
   [51]
   [52]
   [54]
   [55]
   [56]
   [58]
   [60]
   [62]
   [63]
   [64]
   [65]
   [66]
   [68]
   [69]
   [70]
   [72]
   [2012]
   [2748]
   [3040]
   [3770]
   [4462]
   [4514]
   [405238]
   [407206]
   [774524]
   [809920]
   [973124]
   [977602]
   [109468]
   [140010]
   [189802]
   [299794]
   [129826]
   [101667]
   [865068]
   [873150]
   [121]
   [169]
   [289]
   [361]
   [841]
   [10000]
   [555]
   [1075]
   [123]
   [225]
   [961]
   [99995]
   [1369]
   [1405]
   [1681]
   [6969]
   [3333]
   [2209]
   [7895]
   [4445]
   [8885]
   [4489]
   [1675]
   [10201]
   [103335]
   [404120]
   [8645]
   [71365]
   [749834]
   [1000000]
   ]
  )


;; Function that returns the number of factors of a given number. Taken from:
; https://rosettacode.org/wiki/Factors_of_an_integer
(defn expected-output [inputs]
  (let [[n] inputs]
	(count (filter #(zero? (rem n %)) (range 1 (inc n))))))


;; Prime function taken from:
; http://swizec.com/blog/checking-for-primes-dumber-algorithm-is-faster-algorithm/swizec/1580
;; (defn expected-output
;;   [inputs]
;;   (let [[n] inputs]
;;     (if (and (even? n) (not= n 2)) false
;;       (let [root (num (int (Math/sqrt n)))]
;;         (loop [i 3]
;;           (if (> i root) true
;;             (if (zero? (mod n i)) false
;;               (recur (+ i 2)))))))))


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
        top-int (top-item :integer end-state)]
    top-int))


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
  (concat (registered-for-stacks [:integer :boolean :exec])
          (list (fn [] (lrand-int 100))
                'in1)))


(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
