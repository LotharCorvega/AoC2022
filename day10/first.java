package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day10/input.txt"));
		ArrayList<String> instructions = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			instructions.add(scanner.nextLine());
		}

		scanner.close();

		int n = instructions.size();
		int[] register = new int[2 * n];

		register[0] = 1;
		int cycle = 1;

		for (int i = 0; i < n; i++) {
			if (instructions.get(i).equals("noop")) {
				register[cycle] = register[cycle - 1];
			} else {
				int value = Integer.parseInt(instructions.get(i).substring(5));

				register[cycle + 0] = register[cycle - 1];
				register[cycle + 1] = register[cycle - 1] + value;
				cycle++;
			}
			cycle++;
		}

		int sum = 0;

		for (int i = 0; i <= 5; i++) {
			int j = 20 + i * 40;
			sum += j * register[j - 1];
		}

		System.out.println(sum);
	}
}
