package day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day01/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}

		int max1 = 0;
		int max2 = 0;
		int max3 = 0;

		int current = 0;

		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).equals("")) {
				if (current > max1) {
					max3 = max2;
					max2 = max1;
					max1 = current;
				} else if (current > max2) {
					max3 = max2;
					max2 = current;
				} else if (current > max3) {
					max3 = current;
				}

				current = 0;
			} else {
				current += Integer.parseInt(input.get(i));
			}
		}

		System.out.println(max1 + max2 + max3);
	}
}
