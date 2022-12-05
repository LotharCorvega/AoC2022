package day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day03/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}

		int prioritySum = 0;

		for (String rucksack : input) {
			int n = rucksack.length();
			int l = n / 2;

			String compartment1 = rucksack.substring(0, l);
			String compartment2 = rucksack.substring(l, n);

			for (int i = 0; i < l; i++) {
				char item = compartment1.charAt(i);

				if (compartment2.contains(Character.toString(item))) {
					if (Character.isUpperCase(item)) {
						prioritySum += item - 'A' + 27;
					} else {
						prioritySum += item - 'a' + 1;
					}

					break;
				}
			}
		}

		System.out.println(prioritySum);
	}
}
