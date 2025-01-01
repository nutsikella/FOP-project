This is an interpreter written in Java for GOLANG language subset, which includes all simple expressions necessary for implementing given 10 algorithms in the GO language. 

Here is a complete list  of all expressions in given subset of go language:
1. variable type declaration - var n int (only int type)
2. fmt.Print()
3. fmt.Println()   (note: print and println won't execute if theres no input, can only print String and variable inputs seperately, no concatination)
4. fmt.Scan(&n)  //BUG //doesn't work 
5. assignment and declaration operators :=,=
6. for loop with one condition to be checked: for (boolean expression) {} //BUG
7. boolean operators >,<,<=, >=, ==, != //BUG
8. arithmetic operators +,-,/,*,%,++ 
9. if, if-else conditions //BUG
10. return statement that only exits the program, doesn't return any values
