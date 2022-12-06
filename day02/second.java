package day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day02/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}
		
		scanner.close();
		
		int score = 0;
		
		for (String line : input) {			
			switch(line.charAt(2)) {
			case 'X':
				score += getLose(line.charAt(0));
				break;
			case 'Y':
				score += getDraw(line.charAt(0));
				break;
			case 'Z':
				score += getWin(line.charAt(0));
				break;
			}
		}
		
		System.out.println(score);
	}
	
	public static int getWin(char opponent) {
		if (opponent == 'A' ) {	// rock
			return 2 + 6;
		} else if (opponent == 'B' ) {	// paper
			return 3 + 6;
		} else {	// scissors
			return 1 + 6;
		}
	}
	
	public static int getDraw(char opponent) {
		if (opponent == 'A' ) {	// rock
			return 1 + 3;
		} else if (opponent == 'B' ) {	// paper
			return 2 + 3;
		} else {	// scissors
			return 3 + 3;
		}
	}
	
	public static int getLose(char opponent) {
		if (opponent == 'A' ) {	// rock
			return 3 + 0;
		} else if (opponent == 'B' ) {	// paper
			return 1 + 0;
		} else {	// scissors
			return 2 + 0;
		}
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
