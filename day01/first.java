package day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day01/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}

		int max = 0;
		int current = 0;

		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).isEmpty()) {
				max = (max < current) ? current : max;
				current = 0;
			} else {
				current += Integer.parseInt(input.get(i));
			}
		}

		System.out.println(max);
	}
}
