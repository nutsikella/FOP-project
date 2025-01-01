public class testcases {
    // for running testcases separately for each function
    public static void main(String[] args) {
        // // testing for random ints a and b
        String GOcode = """
     a := 25
b := 10

if a < b {
    temp := a
    a = b
    b = temp
}

for b > 0 {  
    temp := b
    b = a % b
    a = temp
}

fmt.Print("The GCD of given numbers is: ")
fmt.Println(a)
        """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
    }
}
