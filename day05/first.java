package day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class Instruction {
	int count;
	int location;
	int destination;
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day05/input.txt"));

		ArrayList<LinkedList<Character>> crates = readCrates(scanner);
		ArrayList<Instruction> instructions = readInstructions(scanner);
		
		scanner.close();

		for (Instruction a : instructions) {
			LinkedList<Character> moved = new LinkedList<>();

			for (int i = 0; i < a.count; i++) {
				moved.addLast(crates.get(a.location - 1).removeFirst());
			}

			for (int i = 0; i < a.count; i++) {
				crates.get(a.destination - 1).addFirst(moved.removeFirst());
			}
		}

		for (LinkedList<Character> x : crates) {
			System.out.print(x.getFirst());
		}
	}

	static ArrayList<LinkedList<Character>> readCrates(Scanner scanner) {
		ArrayList<LinkedList<Character>> crates = new ArrayList<>();

		String firstLine = scanner.nextLine();

		for (int i = 1; i < firstLine.length(); i += 4) {
			LinkedList<Character> list = new LinkedList<>();
			char c = firstLine.charAt(i);

			if (c != ' ') {
				list.addLast(c);
			}

			crates.add(list);
		}

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.isEmpty()) {
				break;
			}

			for (int i = 1; i < line.length(); i += 4) {
				char c = line.charAt(i);

				if (c != ' ') {
					crates.get((i - 1) / 4).addLast(c);
				}
			}
		}

		return crates;
	}

	static ArrayList<Instruction> readInstructions(Scanner scanner) {
		ArrayList<Instruction> instructions = new ArrayList<>();

		while (scanner.hasNext()) {
			Instruction a = new Instruction();

			scanner.next();
			a.count = scanner.nextInt();
			scanner.next();
			a.location = scanner.nextInt();
			scanner.next();
			a.destination = scanner.nextInt();

			instructions.add(a);
		}

		return instructions;
	}
}
