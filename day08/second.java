package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day08/input.txt"));

		String firstLine = scanner.nextLine();
		int size = firstLine.length();

		int[][] grid = new int[size][size];

		for (int x = 0; x < size; x++) {
			grid[x][0] = firstLine.charAt(x) - '0';
		}

		for (int y = 1; y < size; y++) {
			String currentLine = scanner.nextLine();
			for (int x = 0; x < size; x++) {
				grid[x][y] = currentLine.charAt(x) - '0';
			}
		}

		scanner.close();

		int[][] score = new int[size][size];

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				int distLeft = 0;
				int distRight = 0;
				int distDown = 0;
				int distUp = 0;

				// look left
				for (int dx = -1; x + dx >= 0; dx--) {
					distLeft++;
					
					if (grid[x + dx][y] >= grid[x][y]) {
						break;
					}
				}

				// look right
				for (int dx = 1; x + dx < size; dx++) {
					distRight++;
					
					if (grid[x + dx][y] >= grid[x][y]) {
						break;
					}
				}

				// look down
				for (int dy = 1; y + dy < size; dy++) {
					distDown++;
					
					if (grid[x][y + dy] >= grid[x][y]) {
						break;
					}
				}

				// look up
				for (int dy = -1; y + dy >= 0; dy--) {
					distUp++;
					
					if (grid[x][y + dy] >= grid[x][y]) {
						break;
					}
				}
				score[x][y] = distLeft * distRight * distDown * distUp;
			}
		}

		int maxScore = 0;

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (maxScore < score[x][y]) {
					maxScore = score[x][y];
				}
			}
		}

		System.out.println(maxScore);
	}
}
