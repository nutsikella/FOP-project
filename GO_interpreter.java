import java.util.*;

public class GO_interpreter {
    private static final Scanner sc = new Scanner(System.in);
    private final Map<String,Integer> variables = new HashMap<>();  // storage for variables

    public void interpret(String code){
        //splits given code into lines and outline the expression in it 
        String[] lines = code.split("\\n");
        for (String line : lines){
            lineExecution(line.trim());
        }

    }

    private void lineExecution(String line){
        //executes given line according to what code is written in it by calling appropriate functions
        if(line.startsWith("var")){
            variableDeclaration(line);
        }else if (line.startsWith("fmt.Print(")) {
            print(line, false);
        }else if (line.startsWith("fmt.Println(")) {
            print(line, true);
        }else if (line.startsWith("fmt.Scan(")) {
            scan(line);
        }else if (line.contains("=") || line.contains(":=")){
            assignValue(line);
        }else if (line.startsWith("if")){
            evaluateIf(line);
        }else if(line.startsWith("for")){
            forLoop(line);
        }else if(line.startsWith("return")){
            GOreturn(line);
        }

    }

    private void variableDeclaration(String line){
        //handles variable declaration
        String[] parts = line.split(" ");
        // note: interpreter can only parse int variables
        if(parts.length == 3 && parts[2].equals("int")){
            variables.put(parts[1], 0);
        }else{
            throw new IllegalArgumentException("Invalid variable declaration at: " + line);
        }

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

    private void evaluateIf(String line){
        //evaluates If statement 

    }

    private void forLoop(String line){
        // for handling for loops with only 1 condition (while loop version of GO)

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