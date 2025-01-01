public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // testing for random int n
        String GOcode = """
		n := 3

	sum := 0 
	i := 1
	n = n + 1
	for i < n{
		sum = sum + i 
		i = i + 1
	} 
	fmt.Print("The sum of 1st given amount of natural numbers is: ")
	fmt.Println(sum)
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
    }
}
