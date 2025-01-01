public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        String GOcode = """
        n := 4
        k := 8
        if k > n {
            fmt.Println(k)
        } else {
            fmt.Println(n)
        }
        
        for n < 6 {
         n = n + 1
        }
         fmt.Println(n)
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
    }
    
}
