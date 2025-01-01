public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        String GOcode = """
                var n int 
                """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
    }
}
