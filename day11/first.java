package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Monkey {
	Queue<Integer> items;

	boolean squ;
	int add;
	int mul;

	int test;

	int destinationTrue;
	int destinationFalse;

	int inspections;

	public Monkey(Scanner scanner) {
		// Monkey 123...
		scanner.nextLine();

		// Starting items:...
		items = new LinkedList<>();
		String[] startingItems = scanner.nextLine().replace("Starting items:", "").replace(" ", "").split(",");
		for (String item : startingItems) {
			items.add(Integer.parseInt(item)); // bruh
		}

		// Operation:...
		String operation = scanner.nextLine().replace("Operation: new = ", "").replace(" ", "");
		if (operation.equals("old*old")) {
			squ = true;
			add = 0;
			mul = 1;
		} else if (operation.contains("+")) {
			squ = false;
			add = Integer.parseInt(operation.substring(4));
			mul = 1;
		} else if (operation.contains("*")) {
			squ = false;
			add = 0;
			mul = Integer.parseInt(operation.substring(4));
		} else {
			System.out.println("Coulndt convert operation " + operation);
		}

		// Test:
		test = Integer.parseInt(scanner.nextLine().replace("Test: divisible by", "").replace(" ", ""));

		// destination true / false
		destinationTrue = Integer.parseInt(scanner.nextLine().replace("If true: throw to monkey", "").replace(" ", ""));
		destinationFalse = Integer
				.parseInt(scanner.nextLine().replace("If false: throw to monkey", "").replace(" ", ""));

		// newline
		scanner.nextLine();
	}
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day11/input.txt"));
		ArrayList<Monkey> monkeys = new ArrayList<Monkey>();

		while (scanner.hasNextLine()) {
			monkeys.add(new Monkey(scanner));
		}

		for (int round = 0; round < 20; round++) {
			for (Monkey monkey : monkeys) {
				while (!monkey.items.isEmpty()) {
					int item = monkey.items.poll();
					monkey.inspections++;

					if (monkey.squ) {
						item *= item;
					} else {
						item = (item + monkey.add) * monkey.mul;
					}

					item = item / 3;

					if (item % monkey.test == 0) {
						monkeys.get(monkey.destinationTrue).items.add(item);
					} else {
						monkeys.get(monkey.destinationFalse).items.add(item);
					}
				}
			}
		}

		int fmInspections = 0;
		int smInspections = 0;

		for (Monkey monkey : monkeys) {
			if (monkey.inspections > fmInspections) {
				smInspections = fmInspections;
				fmInspections = monkey.inspections;
			} else if (monkey.inspections > smInspections) {
				smInspections = monkey.inspections;
			}

		}

		System.out.println(fmInspections * smInspections);
	}
}
