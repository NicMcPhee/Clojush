CodingBat Problems
=======

Ben Simondet (simon998@morris.umn.edu) 2016

These 7 problems are introductory programming problems with an emphasis on logic from codingbat.com . All were able to be solved, though some needed some exec functions removed to reach a solution. Links to the problems on codingbat can be found within the clojure files.

Problems
--------

Alarm Clock
* Given a day of the week encoded as 0=Sun, 1=Mon, 2=Tue, ...6=Sat, and a boolean indicating if we are on vacation, return a string of the form "7:00" indicating when the alarm clock should ring. Weekdays, the alarm should be "7:00" and on the weekend it should be "10:00". Unless we are on vacation -- then on weekdays it should be "10:00" and weekends it should be "off".
* No modifications were needed to solve the problem.
* Given constants "10:00" "7:00" "off"

Blackjack
* Given 2 int values greater than 0, return whichever value is nearest to 21 without going over. Return 0 if they both go over.
* No modifications were needed to solve the problem.
* Given constants 21 0

Speeding
* You are driving a little too fast, and a police officer stops you. Write code to compute the result, encoded as an int value: 0=no ticket, 1=small ticket, 2=big ticket. If speed is 60 or less, the result is 0. If speed is between 61 and 80 inclusive, the result is 1. If speed is 81 or more, the result is 2. Unless it is your birthday -- on that day, your speed can be 5 higher in all cases.
* No modifications were needed to solve the problem.
* Given constants 1 2 0 60 65 80 85

Tea and Candy Party
* We are having a party with amounts of tea and candy. Return the int outcome of the party encoded as 0=bad, 1=good, or 2=great. A party is good (1) if both tea and candy are at least 5. However, if either tea or candy is at least double the amount of the other one, the party is great (2). However, in all cases, if either tea or candy is less than 5, the party is always bad (0).
* Exec_y needed to be removed to solve this problem.
* Given constant 5
* Analysis of one resulting algorithm can be found [here](https://docs.google.com/a/morris.umn.edu/presentation/d/1SvFOh7V60nvEpo7hqNBTducPxy3KwB2tvXap-i7_EkE/edit?usp=sharing).

Ten In-Between
* Given three ints, a b c, return true if one of them is 10 or more less than one of the others.
* No modifications were needed to solve this problem. 
* Given constant 10

Three Value Sum
* Given 3 int values, a b c, return their sum. However, if one of the values is the same as another of the values, it does not count towards the sum.
* Exec_y needed to be removed to solve this problem.
* No constants given

Two Value Forbidden Sum
* Given 2 ints, a and b, return their sum. However, sums in the range 10..19 inclusive, are forbidden, so in that case just return 20.
* Exec_y, exec_k, and exec_s needed to be removed to ensure a solution was reached every time
* Given constants 10 and 19
* Analysis of one resulting algorithm can be found [here](https://docs.google.com/a/morris.umn.edu/presentation/d/1SvFOh7V60nvEpo7hqNBTducPxy3KwB2tvXap-i7_EkE/edit?usp=sharing).
