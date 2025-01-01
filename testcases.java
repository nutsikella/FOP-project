public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // testing for random int n
        String GOcode = """
	n := 1234
	var m int

	fmt.Print("Reversed: ")
	for n > 0 {
		m = n % 10
		fmt.Print(m)
		n = n/10
	}
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
        // output: 5 
    }
    
}
