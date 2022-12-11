package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day11/input.txt"));
		ArrayList<Monkey> monkeys = new ArrayList<Monkey>();

		while (scanner.hasNextLine()) {
			monkeys.add(new Monkey(scanner));
		}

		// Well not lcm but they prime
		int lcm = 1;
		for (Monkey monkey : monkeys) {
			lcm *= monkey.test;
		}

		for (int round = 0; round < 10000; round++) {
			for (Monkey monkey : monkeys) {
				while (!monkey.items.isEmpty()) {
					long item = (long) monkey.items.poll();
					monkey.inspections++;

					if (monkey.squ) {
						item *= item;
					} else {
						item = (item + monkey.add) * monkey.mul;
					}

					item %= lcm;

					if (item % monkey.test == 0) {
						monkeys.get(monkey.destinationTrue).items.add((int) item);
					} else {
						monkeys.get(monkey.destinationFalse).items.add((int) item);
					}
				}
			}
		}

		long fmInspections = 0;
		long smInspections = 0;

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
