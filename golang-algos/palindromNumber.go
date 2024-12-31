package main

//note that In Go (and most programming languages), 
//integers don't store leading zeros. When you enter 000000 as input, 
//it is interpreted as just 0, which is why the program outputs 0 is a palindrome.
//so the program will print that 0 is a palindrome

/*
import ("fmt")

func main(){
	var n int
	fmt.Print("Enter a number n: ")
	fmt.Scan(&n)

	original := n
	fmt.Println(original)
	
	var endDigit int
	var reversed int 
	
	for n > 0{
		endDigit = n % 10 
		reversed = reversed*10 + endDigit
		n = n/10
	}
	if reversed == original {
		fmt.Println("Given number is a Palindrome")
	}else{
		fmt.Println("Given number isn't a Palindrome")
	}
}
*/

