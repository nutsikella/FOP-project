public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // testing for random int n
        String GOcode = """
	n := 13

    if n < 2 {
        fmt.Println("Given number isn't a prime number")
        return
    }

    i := 2
    k := i * i
    f := n + 1
    for k < f {
        remainder := n % i
        if remainder > 0 { 
            i = i + 1
            k = i * i
        } else {
            fmt.Println("Given number isn't a prime number")
            return
        }
    }
    fmt.Println("Given number is a prime number")
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
        // output: 5 
    }
    
}
