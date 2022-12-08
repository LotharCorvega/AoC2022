package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class first {
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

		boolean[][] visible = new boolean[size][size];

		// visible from left
		for (int y = 0; y < size; y++) {
			visible[0][y] = true;
			int current = grid[0][y];

			for (int x = 1; x < size; x++) {
				if (grid[x][y] > current) {
					visible[x][y] = true;
					current = grid[x][y];
				}
			}
		}

		// visible from right
		for (int y = 0; y < size; y++) {
			visible[size - 1][y] = true;
			int current = grid[size - 1][y];

			for (int x = size - 2; x >= 0; x--) {
				if (grid[x][y] > current) {
					visible[x][y] = true;
					current = grid[x][y];
				}
			}
		}

		// visible from above
		for (int x = 0; x < size; x++) {
			visible[x][0] = true;
			int current = grid[x][0];

			for (int y = 1; y < size; y++) {
				if (grid[x][y] > current) {
					visible[x][y] = true;
					current = grid[x][y];
				}
			}
		}

		// visible from below
		for (int x = 0; x < size; x++) {
			visible[x][size - 1] = true;
			int current = grid[x][size - 1];

			for (int y = size - 2; y >= 0; y--) {
				if (grid[x][y] > current) {
					visible[x][y] = true;
					current = grid[x][y];
				}
			}
		}

		int count = 0;
		
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (visible[x][y]) {
					count++;
				}
			}
		}

		System.out.println(count);
	}
}
