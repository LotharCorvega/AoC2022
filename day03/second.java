package day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day03/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}

		int prioritySum = 0;

		for (int i = 0; i < input.size(); i += 3) {

			String rucksack1 = input.get(i + 0);
			String rucksack2 = input.get(i + 1);
			String rucksack3 = input.get(i + 2);

			for (int j = 0; j < rucksack1.length(); j++) {
				char item = rucksack1.charAt(j);
				String s = Character.toString(item);

				if (rucksack2.contains(s) && rucksack3.contains(s)) {
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
