public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // testing for random int n
        String GOcode = """
		   n := 123

	sum := 0
	for n > 0{
		sum = sum + n % 10
		n = n/10
	}
	fmt.Print("Sum of the digits of given number: ")
	fmt.Println(sum)
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
    }
}
