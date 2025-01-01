import java.util.*;

public class GO_interpreter {
    private static final Scanner sc = new Scanner(System.in);
    private final Map<String, Integer> variables = new HashMap<>();

    public void interpret(String code) {
        String[] lines = code.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            //modified so that it can handle if-else or loops that span multiple lines
            if (line.startsWith("if") || line.startsWith("for")) {
                i = handleBlock(lines, i);
            } else {
                lineExecution(line);
            }
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
            return Integer.parseInt(expr);
        } catch (NumberFormatException e) {
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
                            if (rightValue == 0) throw new ArithmeticException("Division by zero");
                            return leftValue / rightValue;
                        case "%":
                            if (rightValue == 0) throw new ArithmeticException("Modulo by zero");
                            return leftValue % rightValue;
                    }
                }
            }
            if (variables.containsKey(expr)) {
                return variables.get(expr);
            }
            throw new IllegalArgumentException("Invalid or Undefined arithmetic expression: " + expr);
        }
    }

    private boolean evaluateCondition(String condition) {
        String[] operators = {"<=", ">=", "==", "<", ">"};
        for (String operator : operators) {
            int operatorIndex = condition.indexOf(operator);
            if (operatorIndex != -1) {
                String left = condition.substring(0, operatorIndex).trim();
                String right = condition.substring(operatorIndex + operator.length()).trim();
                int leftValue = evaluateArithmetic(left);
                int rightValue = evaluateArithmetic(right);

                switch (operator) {
                    case "<=":
                        return leftValue <= rightValue;
                    case ">=":
                        return leftValue >= rightValue;
                    case "==":
                        return leftValue == rightValue;
                    case "<":
                        return leftValue < rightValue;
                    case ">":
                        return leftValue > rightValue;
                }
            }
        }
        throw new IllegalArgumentException("Invalid condition: " + condition);
    }

    private int handleBlock(String[] lines, int startIndex) {
        String line = lines[startIndex].trim();
        int braceCount = 0;
        StringBuilder blockContent = new StringBuilder();

        do {
            line = lines[startIndex].trim();
            if (line.contains("{")) braceCount++;
            if (line.contains("}")) braceCount--;
            blockContent.append(line).append("\n");
            startIndex++;
        } while (braceCount > 0 && startIndex < lines.length);

        String block = blockContent.toString().trim();
        if (block.startsWith("if")) {
            handleIfElseBlock(block);
        } else if (block.startsWith("for")) {
            handleForBlock(block);
        }
        return startIndex - 1;
    }

    private void handleIfElseBlock(String block) {
        int elseIndex = block.indexOf("else");
        String ifPart = elseIndex == -1 ? block : block.substring(0, elseIndex).trim();
        String elsePart = elseIndex == -1 ? "" : block.substring(elseIndex + 4).trim();

        String condition = ifPart.substring(3, ifPart.indexOf("{")).trim();
        String ifBody = ifPart.substring(ifPart.indexOf("{") + 1, ifPart.lastIndexOf("}")).trim();

        if (evaluateCondition(condition)) {
            interpret(ifBody);
        } else if (!elsePart.isEmpty()) {
            String elseBody = elsePart.substring(elsePart.indexOf("{") + 1, elsePart.lastIndexOf("}")).trim();
            interpret(elseBody);
        }
    }

    private void handleForBlock(String block) {
        String condition = block.substring(4, block.indexOf("{")).trim();
        String body = block.substring(block.indexOf("{") + 1, block.lastIndexOf("}")).trim();

        while (evaluateCondition(condition)) {
            interpret(body);
        }
    }
}
