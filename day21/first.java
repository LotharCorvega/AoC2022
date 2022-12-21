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
	public abstract long evaluate();
}

class BasicMonkey extends Monkey {
	long value;

	public BasicMonkey(long value) {
		this.value = value;
	}

	@Override
	public long evaluate() {
		return value;
	}

	@Override
	public String toString() {
		return Long.toString(value);
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
	public long evaluate() {
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

	@Override
	public String toString() {
		switch (type) {
		case ADD:
			return "(" + monkeys.get(leftMonkeyName).toString() + "+" + monkeys.get(rightMonkeyName).toString() + ")";
		case SUBTRACT:
			return "(" + monkeys.get(leftMonkeyName).toString() + "-" + monkeys.get(rightMonkeyName).toString() + ")";
		case MULTIPLY:
			return "(" + monkeys.get(leftMonkeyName).toString() + "*" + monkeys.get(rightMonkeyName).toString() + ")";
		case DIVIDE:
			return "(" + monkeys.get(leftMonkeyName).toString() + "/" + monkeys.get(rightMonkeyName).toString() + ")";
		default:
			return "Error";
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
				monkeys.put(arguments[0], new BasicMonkey(Integer.parseInt(arguments[1])));
			}
		}

		System.out.println(monkeys.get("root").evaluate());
	}
}
