package day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day02/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}
		
		scanner.close();
		
		int score = 0;
		
		for (String line : input) {
			score += getScore(line.charAt(0), line.charAt(2));
		}
		
		System.out.println(score);
	}
	
	public static int getScore(char opponent, char play) {		
		if (opponent == 'A' ) {	// rock
			if (play == 'X') {
				return 3 + 1;
			} else if (play == 'Y' ) {
				return 6 + 2;
			} else {
				return 0 + 3;
			}			
		} else if (opponent == 'B' ) {	// paper
			if (play == 'X') {
				return 0 + 1;
			} else if (play == 'Y' ) {
				return 3 + 2;
			} else {
				return 6 + 3;
			}	
		} else {	// scissors
			if (play == 'X') {
				return 6 + 1; 
			} else if (play == 'Y' ) {
				return 0 + 2;
			} else {
				return 3 + 3;
			}	
		}
	}
}
