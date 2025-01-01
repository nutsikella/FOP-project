public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // testing for random int n
        String GOcode = """
	n := 12

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
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
        // output: 5 
    }
    
}
