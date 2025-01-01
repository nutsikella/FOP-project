public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // testing for random int n instead of using scanner 
        String GOcode = """
        n := 3

	    var fib int 
	    f1 := 0 
	    f2 := 1
	if n < 0 {
		fmt.Println("Fibonacci number is not defined for negative indices.")
		return
	}
	if n == 0 {
		fmt.Print("Fibonacci number at given index is : ")
		fmt.Println(f1)
		return
	}
	if n == 1 {
		fmt.Print("Fibonacci number at given index is : ")
		fmt.Println(f2)
		return
	}else {
		i := 2
		for i <= n{
			fib = f1 + f2
			f1 = f2
			f2 = fib
			i = i + 1
		}
	}
		fmt.Print("Fibonacci number at given index is : ")
		fmt.Println(fib)
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
        // output: 2 
    }
    
}
