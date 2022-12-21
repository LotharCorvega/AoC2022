package day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class VariableMonkey extends Monkey {

	@Override
	public long evaluate() {
		return 0;
	}

	@Override
	public String toString() {
		return "x";
	}
}

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day21/input.txt"));
		Map<String, Monkey> monkeys = new HashMap<>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] arguments = line.split("(: )|( + )|( \\\\- )|( * )|( / )");

			if (line.contains("+")) {
				monkeys.put(arguments[0], new OperationMonkey(OPTYPE.ADD, arguments[1], arguments[3], monkeys));
			} else if (line.contains("-")) {
				monkeys.put(arguments[0], new OperationMonkey(OPTYPE.SUBTRACT, arguments[1], arguments[3], monkeys));
			} else if (line.contains("*")) {
				monkeys.put(arguments[0], new OperationMonkey(OPTYPE.MULTIPLY, arguments[1], arguments[3], monkeys));
			} else if (line.contains("/")) {
				monkeys.put(arguments[0], new OperationMonkey(OPTYPE.DIVIDE, arguments[1], arguments[3], monkeys));
			} else {
				monkeys.put(arguments[0], new BasicMonkey(Integer.parseInt(arguments[1])));
			}
		}

		monkeys.replace("humn", new VariableMonkey());

		OperationMonkey root = (OperationMonkey) monkeys.get("root");

		String leftTerm = monkeys.get(root.leftMonkeyName).toString();
		String rightTerm = monkeys.get(root.rightMonkeyName).toString();

		System.out.println("Use some website for this:");
		System.out.println(leftTerm + " = " + rightTerm);
	}
}
