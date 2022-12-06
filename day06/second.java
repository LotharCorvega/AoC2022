package day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day06/input.txt"));
		String message = scanner.nextLine();
		scanner.close();

		int markerSize = 14;
		int count = 0;

		for (int i = markerSize; i <= message.length(); i++) {
			if (isMarker(message.substring(i - markerSize, i))) {
				count += i;
				break;
			}
		}
		
		System.out.println(count);
	}

	public static boolean isMarker(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c0 = s.charAt(i);

			for (int j = 0; j < s.length(); j++) {
				char c1 = s.charAt(j);

				if (j != i && c0 == c1) {
					return false;
				}
			}
		}

		return true;
	}
}