import java.util.*;
public class GO_interpreter {
    private static final Scanner sc = new Scanner(System.in);
    private final Map<String, Integer> variables = new HashMap<>();

    public void interpret(String code) {
        String[] lines = code.split("\\n");
        for (String line : lines) {
            lineExecution(line.trim());
        }
    }

    private void lineExecution(String line) {
        if (line.startsWith("var")) {
            variableDeclaration(line);
        } else if (line.startsWith("fmt.Print(")) {
            print(line, false);
        } else if (line.startsWith("fmt.Println(")) {
            print(line, true);
        } else if (line.startsWith("fmt.Scan(")) {
            scan(line);
        } else if (line.contains("=") || line.contains(":=")) {
            assignValue(line);
        } else if (line.startsWith("if")) {
            evaluateIf(line);
        } else if (line.startsWith("for")) {
            forLoop(line);
        } else if (line.startsWith("return")) {
            GOreturn(line);
        }
    }

    private void variableDeclaration(String line) {
        String[] parts = line.split(" ");
        if (parts.length == 3 && parts[2].equals("int")) {
            variables.put(parts[1], 0);
        } else {
            throw new IllegalArgumentException("Invalid variable declaration: " + line);
        }
    }

    private void print(String line, boolean newline) {
        String expr = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).trim();
        if (expr.startsWith("\"") && expr.endsWith("\"")) {
            // Print a string literal
            System.out.print(expr.substring(1, expr.length() - 1) + (newline ? "\n" : ""));
        } else if (variables.containsKey(expr)) {
            // Print the value of a variable
            System.out.print(variables.get(expr) + (newline ? "\n" : ""));
        } else {
            throw new IllegalArgumentException("Invalid fmt.Print() or fmt.Println() expression: " + line);
        }
    }
    

    private void scan(String line) {
        String varName = line.substring(line.indexOf('&') + 1, line.lastIndexOf(')')).trim();
        if (variables.containsKey(varName)) {
            int value = sc.nextInt();
            variables.put(varName, value);
        } else {
            throw new IllegalArgumentException("Invalid or Undefined variable in fmt.Scan: " + line);
        }
    }

    private void assignValue(String line) {
        String[] parts = line.split("[:=]+", 2);

        String varName = parts[0].trim();
        String expr = parts[1].trim();

        int value = evaluateArithmetic(expr);
        if (!variables.containsKey(varName) && line.contains(":=")) {
            variables.put(varName, value);
        } else if (variables.containsKey(varName)) {
            variables.put(varName, value);
        } else {
            throw new IllegalArgumentException("Invalid variable assignment: " + varName);
        }
    }

    private int evaluateArithmetic(String expr) {
        try {
            // Evaluate directly if it's a simple number
            return Integer.parseInt(expr);
        } catch (NumberFormatException e) {
            // Handle arithmetic expressions
            String[] operators = {"+", "-", "*", "/", "%"};
            for (String operator : operators) {
                int operatorIndex = expr.indexOf(operator);
                if (operatorIndex != -1) {
                    String leftOperand = expr.substring(0, operatorIndex).trim();
                    String rightOperand = expr.substring(operatorIndex + 1).trim();
                    int leftValue = evaluateArithmetic(leftOperand);
                    int rightValue = evaluateArithmetic(rightOperand);
                    switch (operator) {
                        case "+":
                            return leftValue + rightValue;
                        case "-":
                            return leftValue - rightValue;
                        case "*":
                            return leftValue * rightValue;
                        case "/":
                            if (rightValue == 0) {
                                throw new ArithmeticException("Division by zero in expression: " + expr);
                            }
                            return leftValue / rightValue;
                        case "%":
                            if (rightValue == 0) {
                                throw new ArithmeticException("Modulo by zero in expression: " + expr);
                            }
                            return leftValue % rightValue;
                    }
                }
            }
    
            // Check if it's a variable
            if (variables.containsKey(expr)) {
                return variables.get(expr);
            }
    
            throw new IllegalArgumentException("Invalid or Undefined arithmetic expression: " + expr);
        }
    }
    
    private void evaluateIf(String line) {
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

    private void forLoop(String line) {
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

    private void GOreturn(String line) {
        System.exit(0);
    }
}
