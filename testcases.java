public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        //removed scanner, so the code will be tested for a random number n 
        String GOcode = """
        n := 3
	    sum := 1

	    i := 1
	    for  i <= n{
		sum = sum * i
		i = i + 1
	    }

	    fmt.Print("The factorial of given number is ")
	    fmt.Println(sum)
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
    }
    
}
