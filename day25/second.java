package day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day00/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}
	}
}
