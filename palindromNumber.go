package main

//note that In Go (and most programming languages), 
//integers don't store leading zeros. When you enter 000000 as input, 
//it is interpreted as just 0, which is why the program outputs 0 is a palindrome.
//so the program will print that 0 is a palindrome

/*
import ("fmt")

func main(){
// testing for random int n
	n := 121

	original := n
	
	var endDigit int
	var reversed int 
	
	for n > 0{
		endDigit = n % 10 
		reversed = reversed*10 + endDigit
		n = n/10
	}

	if reversed > original {
		fmt.Println("Given number isn't a Palindrome")
		return
	}
	if reversed < original {
		fmt.Println("Given number isn't a Palindrome")
		return
	}
		fmt.Println("Given number is a Palindrome")
}
*/

