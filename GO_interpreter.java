import java.util.*;

public class GO_interpreter {
    private static final Scanner sc = new Scanner(System.in);
    private final Map<String,Integer> variables = new HashMap<>();  // storage for variables

    public void interpret(String code){
        //splits given code into lines and outline the expression in it 

    }

    private void lineExecution(String line){
        //executes given line according to what code is written in it by calling appropriate functions

    }

    private void variableDeclaration(String line){
        //handles variable declaration

    }

    private void print(String line, boolean newline){
        // handles print() and println() statements

    }

    private void scan(String line){
        // handles scan statement 
        
    }

    private void assignValue(String line){
        // handles variable assignment

    }

    private int evaluateArithmetic(String expr){
        // evaluates arithemtic expressions

        return 0;
    }

    private void evaluateBoolean(String line){
        //evaluates boolean conditions 

    }

    private void forLoop(String line){
        // for handling for loops

    }

    private void GOreturn(String line){
        // handles return statement which finishes the execution 

    }

    public static void main(String[] args) {
        String GOcode = """
                var n int
	            fmt.Print("Enter a number n: ")
	            fmt.Scan(&n)
                sum := 1
                i := 1
                for  i <= n{
                sum = sum * i
                i++
                }
                fmt.Print("The factorial of given number is ")
                fmt.Println(sum)
                """;
        GO_interpreter interpreter = new GO_interpreter();
        interpreter.interpret(GOcode);
    }

}