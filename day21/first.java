package day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

enum OPTYPE {
	ADD, SUBTRACT, MULTIPLY, DIVIDE
}

abstract class Monkey {
	public abstract double evaluate();
}

class BasicMonkey extends Monkey {
	double value;

	public BasicMonkey(double value) {
		this.value = value;
	}

	@Override
	public double evaluate() {
		return value;
	}
}

class OperationMonkey extends Monkey {

	OPTYPE type;
	String leftMonkeyName;
	String rightMonkeyName;
	Map<String, Monkey> monkeys;

	public OperationMonkey(OPTYPE type, String leftMonkeyName, String rightMonkeyName, Map<String, Monkey> monkeys) {
		this.type = type;
		this.leftMonkeyName = leftMonkeyName;
		this.rightMonkeyName = rightMonkeyName;
		this.monkeys = monkeys;
	}

	@Override
	public double evaluate() {
		switch (type) {
		case ADD:
			return monkeys.get(leftMonkeyName).evaluate() + monkeys.get(rightMonkeyName).evaluate();
		case SUBTRACT:
			return monkeys.get(leftMonkeyName).evaluate() - monkeys.get(rightMonkeyName).evaluate();
		case MULTIPLY:
			return monkeys.get(leftMonkeyName).evaluate() * monkeys.get(rightMonkeyName).evaluate();
		case DIVIDE:
			return monkeys.get(leftMonkeyName).evaluate() / monkeys.get(rightMonkeyName).evaluate();
		default:
			System.out.println("Error");
			return 0;
		}
	}
}

public class first {
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
				monkeys.put(arguments[0], new BasicMonkey(Double.parseDouble(arguments[1])));
			}
		}

		System.out.println((long) monkeys.get("root").evaluate());
	}
}
