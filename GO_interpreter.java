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
            throw new IllegalArgumentException("Invalid variable declaration : " + line);
        }

    }

    private void print(String line, boolean newline){
        // handles print() and println() statements
        // extra newline param lets us control whether we should add new line after printing
        String expr = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).trim();
        if (expr.startsWith("\"") && expr.endsWith("\"")){
            // print onlt the string given as input
            System.out.print(expr.substring(1,expr.length() - 1)+ (newline ? "\n" : ""));
        }else if(variables.containsKey(expr)){
            //print value of a variable
            System.out.println(variables.get(expr) + (newline ? "\n" : ""));
        }else {
            throw new IllegalArgumentException("Invalid fmt.Print() or fmt.Println() expression : " + line);
        }

    }

    private void scan(String line){
        // handles scan statement 
        String varName = line.substring(line.indexOf('&') + 1, line.lastIndexOf(')')).trim();
        if(variables.containsKey(varName)){
            int value = sc.nextInt();
            variables.put(varName, value);
        }else{
            throw new IllegalArgumentException("Invalid or Undefined variable in fmt.Scan : " + line);
        }
        
    }
    private void assignValue(String line){
        // handles variable assignment
       
        String[] parts = line.split("[=:]+",2);

        String varName = parts[0].trim();
        String expr = parts[1].trim();

        int value = evaluateArithmetic(expr);
        if(!variables.containsKey(varName) && line.contains(":=")){
            variables.put(varName, value);
        }else if(variables.containsKey(varName)){
            variables.put(varName, value);
        }else{
            throw new IllegalArgumentException("Invalid variable assignment : " + varName);
        }

    }

    private int evaluateArithmetic(String expr){
        // evaluate Arithmetic expression
        try {
            // POSSIBLE BUG
            return Integer.parseInt(expr);
        } catch (NumberFormatException e) {
            if(variables.containsKey(expr)){
                return variables.get(expr);
            }else{
                throw new IllegalArgumentException("Invalid or Undefined arithmetic expression : " + expr);
            }
        }
    }

    private void evaluateIf(String line){
        //evaluates If statement 
        if (line.startsWith("if ")) {
            String condition = line.substring(3, line.indexOf("{")).trim();
            String body = line.substring(line.indexOf("{") + 1, line.lastIndexOf("}"));

            if (evaluateCondition(condition)) {
                interpret(body);
            }
        } else {
            throw new IllegalArgumentException("Invalid if statement: " + line);
        }

    }
    private boolean evaluateCondition(String condition) {
        // Simple evaluation of conditions (supports variables and constants)
        String[] parts = condition.split("<=|>=|==|<|>|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid condition: " + condition);
        }

        int left = evaluateArithmetic(parts[0].trim());
        int right = evaluateArithmetic(parts[1].trim());

        if (condition.contains("<=")) {
            return left <= right;
        } else if (condition.contains(">=")) {
            return left >= right;
        } else if (condition.contains("==")) {
            return left == right;
        } else if (condition.contains("<")) {
            return left < right;
        } else if (condition.contains(">")) {
            return left > right;
        } else {
            throw new IllegalArgumentException("Unsupported condition: " + condition);
        }
    }

    private void forLoop(String line){
        // for handling for loops with only 1 condition (while loop version of GO)
        if (line.startsWith("for ")) {
            String condition = line.substring(4, line.indexOf("{")).trim();
            String body = line.substring(line.indexOf("{") + 1, line.lastIndexOf("}"));

            while (evaluateCondition(condition)) {
                interpret(body);
            }
        } else {
            throw new IllegalArgumentException("Invalid for loop: " + line);
        }
    }

    private void GOreturn(String line){
        // handles return statement which finishes the execution 
        System.exit(0);
    }

}