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
    
    private void evaluateIf(String code) {
        if (!code.startsWith("if ")) {
            throw new IllegalArgumentException("Invalid if statement: " + code);
        }
    
        // Extract the condition
        int ifConditionStart = 3; // Start after "if "
        int ifConditionEnd = code.indexOf("{");
        if (ifConditionEnd == -1) {
            throw new IllegalArgumentException("Malformed if statement: Missing '{': " + code);
        }
        String condition = code.substring(ifConditionStart, ifConditionEnd).trim();
    
        // Extract the 'if' block
        int ifBodyStart = ifConditionEnd + 1; // Start after '{'
        int ifBodyEnd = findMatchingBrace(code, ifBodyStart);
        if (ifBodyEnd == -1) {
            throw new IllegalArgumentException("Malformed if statement: Missing '}' for if block: " + code);
        }
        String ifBody = code.substring(ifBodyStart, ifBodyEnd).trim();
    
        // Extract the 'else' block, if it exists
        String elseBody = null;
        int elseIndex = code.indexOf("else", ifBodyEnd);
        if (elseIndex != -1) {
            int elseBodyStart = code.indexOf("{", elseIndex);
            int elseBodyEnd = findMatchingBrace(code, elseBodyStart + 1);
            if (elseBodyStart == -1 || elseBodyEnd == -1) {
                throw new IllegalArgumentException("Malformed else statement: Missing braces for else block: " + code);
            }
            elseBody = code.substring(elseBodyStart + 1, elseBodyEnd).trim();
        }
    
        // Evaluate the condition and execute the appropriate block
        if (evaluateCondition(condition)) {
            interpret(ifBody);
        } else if (elseBody != null) {
            interpret(elseBody);
        }
    }
    
    // Utility function to find the matching closing brace for a given opening brace
    private int findMatchingBrace(String code, int start) {
        int braceCount = 0;
        for (int i = start; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c == '{') {
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    return i; // Found the matching closing brace
                }
            }
        }
        return -1; // No matching brace found
    }
    

    private boolean evaluateCondition(String condition) {
        String[] operators = {"<=", ">=", "==", "!=", "<", ">"};
        for (String operator : operators) {
            int operatorIndex = condition.indexOf(operator);
            if (operatorIndex != -1) {
                String leftOperand = condition.substring(0, operatorIndex).trim();
                String rightOperand = condition.substring(operatorIndex + operator.length()).trim();
    
                int leftValue = evaluateArithmetic(leftOperand);
                int rightValue = evaluateArithmetic(rightOperand);
    
                switch (operator) {
                    case "<=":
                        return leftValue <= rightValue;
                    case ">=":
                        return leftValue >= rightValue;
                    case "==":
                        return leftValue == rightValue;
                    case "!=":
                        return leftValue != rightValue;
                    case "<":
                        return leftValue < rightValue;
                    case ">":
                        return leftValue > rightValue;
                }
            }
        }
        throw new IllegalArgumentException("Unsupported or invalid condition: " + condition);
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
