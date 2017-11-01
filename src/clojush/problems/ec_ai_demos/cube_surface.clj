;; cube_surface.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Nic McPhee, mcphee@morris.umn.edu, 2016

(ns clojush.problems.ec-ai-demos.cube-surface
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;
;; Take three non-negative integers that represent the width, height
;; and length of a cubic volume, and push an integer that is the
;; surface area of this shape.

(def input-set
  [[0 0 0]
   [0 2 3]
   [0 14 8]
   [4 0 3]
   [15 0 7]
   [3 5 0]
   [10 6 0]
   [1 1 1]
   [1 1 2]
   [1 1 6]
   [1 3 1]
   [1 19 1]
   [4 1 1]
   [11 1 1]
   [1 3 5]
   [5 1 4]
   [3 4 1]
   [1 15 13]
   [1 10 3]
   [5 1 12]
   [6 1 2]
   [14 15 1]
   [17 12 1]
   [1 17 9]
   [1 13 2]
   [2 1 14]
   [19 1 11]
   [7 4 1]
   [14 8 1]
   [2 2 2]
   [2 15 7]
   [10 6 2]
   [12 2 8]
   [2 6 8]
   [5 8 9]
   [18 7 13]
   [4 9 20]
   [8 20 7]
   [13 3 16]
   [7 15 3]
   [2 3 5]
   [18 19 13]
   [3 10 13]
   [9 20 14]
   [20 16 12]])

(defn expected-output
  [inputs]
  (let [[x y z] inputs]
    (* 2
       (+ (* x y)
          (* x z)
          (* y z)))))

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

(defn abs [n]
  (if (< n 0)
    (- n)
    n))

(defn all-errors
  [program]
  (doall
    (for [inputs input-set]
      (let [expected (expected-output inputs)
            actual (actual-output program inputs)]
        (if (= actual :no-stack-item)
          1000
          (abs (- expected actual)))))))

(def atom-generators
  (concat
    (registered-for-stacks [:integer]) ;  :boolean]) ;  :exec])
    (list 'exec_dup)
    (list (fn [] (lrand-int 100)))
    (list 'in1 'in2 'in3)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   })
