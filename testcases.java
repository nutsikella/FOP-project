public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // testing for random ints a and b instead of using scanner 
        String GOcode = """
    n := 297

	largest := 0
	for n > 0{
	    k := n % 10
		if k > largest{
			largest = k
		}
		n = n/10
	}
	fmt.Print("largest digit is : ")
	fmt.Println(largest)
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
        // output: 5 
    }
    
}
