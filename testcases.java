public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // testing for random int n
        String GOcode = """
			n := 3

	fmt.Println("Multiplication table for given number: ")
	i := 1
	for  i < 11{
	    k := i*n
		fmt.Println(k)
		i = i + 1
	}
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
        // output: 5 
    }
    
}
