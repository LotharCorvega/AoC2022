package day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

		OperationMonkey root = (OperationMonkey) monkeys.get("root");
		Monkey leftMonkey = monkeys.get(root.leftMonkeyName);
		Monkey rightMonkey = monkeys.get(root.rightMonkeyName);

		BasicMonkey humnMonkey = (BasicMonkey) monkeys.get("humn");

		// Binary search
		double low = Long.MIN_VALUE >> 16;
		double high = Long.MAX_VALUE >> 16;

		while (low <= high) {
			long mid = (long) (low + high) / 2;
			humnMonkey.value = mid;

			double midVal = rightMonkey.evaluate() - leftMonkey.evaluate();

			if (midVal < 0) {
				low = mid + 1;
			} else if (midVal > 0) {
				high = mid - 1;
			} else {
				break;
			}
		}

		System.out.println((long) humnMonkey.value);
	}
}
