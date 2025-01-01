Project Title: Java Interpreter for defined subset of GO language
Team member: Nutsa Chikhladze

This repository contains code for interpreter in java written for defined subset of GO language (GO_interpreter.java),
along with an extra file(testcases.java) in which you can test given interpreter for given algorithms. All the files written in GO (files ending with .go) are implementations of given algorithms in GO language.

You can check the correctnes of given interpreter for defined algorithms by seeing the commit history of testcases.java.
You can also write code in the interpreter but ONLY for the defined subset of GO language.


Here is a complete list  of all expressions in defined subset of go language:
1. variable type declaration - var n int (only int type)
2. fmt.Print()
3. fmt.Println()   (note: print and println won't execute if theres no input, can only print String and variable inputs seperately, no concatination)
4. assignment and declaration operators :=,=
5. for loop with one condition to be checked: for (boolean expression) {} 
6. boolean operators >,<
7. arithmetic operators +,-,/,*,%
8. if, if-else conditions 
9. return statement that only exits the program, doesn't return any values


List of algorithms: 
1. factorial: works
2. fibonacci: works 
3. GCD: works
4. largest digit: works 
5. multiplication table: works 
6. reversing a number: works
7. palindrome number: works
8. prime number: works
9. sum: works
10. sum of digits: works
