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
  [[1]
   [2]
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
   [1059467]
   [1240013]
   [1289803]
   [1299791]
   [1299827]
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
   [12112319]
   [12114643]
   [171102427]
   [171103267]
   [171112751]
   [1711100539]
   [1711104151]
   [1711102177]
   [17110979363]
   [17110985467]
   [17110994561]
   [17111009999]
   [15111006583]
   [15111009653]
   [15111013849]
   [35111012231]
   [35111011079]
   [35111012357]
   [35111003797]
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
   [1059468]
   [1240010]
   [1289802]
   [1299794]
   [1299826]
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
   [10335365569]
   [4044121]
   [8645]
   [71365]
   [749831642895]
   [713684295]
   ]
  )


;; Prime function taken from:
; http://swizec.com/blog/checking-for-primes-dumber-algorithm-is-faster-algorithm/swizec/1580
(defn expected-output
  [inputs]
  (let [[n] inputs]
    (if (and (even? n) (not= n 2)) false
      (let [root (num (int (Math/sqrt n)))]
        (loop [i 3]
          (if (> i root) true
            (if (zero? (mod n i)) false
              (recur (+ i 2)))))))))


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
  (concat (registered-for-stacks [:integer :boolean :exec])
          (list (fn [] (lrand-int 100))
                'in1)))


(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
