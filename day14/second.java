package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day14/input.txt")).useDelimiter(",|( -> )|\\s");
		ArrayList<Path> paths = new ArrayList<>();

		int maxY = 0;

		while (scanner.hasNextLine()) {
			Path path = new Path();

			while (scanner.hasNextInt()) {
				int x = scanner.nextInt();
				int y = scanner.nextInt();

				if (y > maxY) {
					maxY = y;
				}

				path.points.add(new Point(x, y));
			}

			scanner.nextLine();
			paths.add(path);
		}

		char[][] grid = new char[400][200]; // hard

		for (Path path : paths) {
			for (int i = 0; i < path.points.size() - 1; i++) {
				Point p1 = path.points.get(i + 0);
				Point p2 = path.points.get(i + 1);

				int x1 = Math.min(p1.x, p2.x) - 300; // hard
				int x2 = Math.max(p1.x, p2.x) - 300; // hard

				int y1 = Math.min(p1.y, p2.y);
				int y2 = Math.max(p1.y, p2.y);

				if (x1 == x2) {
					for (int y = y1; y <= y2; y++) {
						grid[x1][y] = '#';
					}
				} else if (y1 == y2) {
					for (int x = x1; x <= x2; x++) {
						grid[x][y1] = '#';
					}
				}
			}
		}

		for (int x = 0; x < grid.length; x++) {
			grid[x][maxY + 2] = '#';
		}

		int count = 0;

		while (grid[200][0] == 0) { // hard
			spawnSand(grid);
			count++;
		}

		System.out.println(count);
	}

	public static void spawnSand(char[][] grid) {
		Point sand = new Point(200, 0); // hard
		boolean isFalling = true;

		while (isFalling) {
			if (grid[sand.x][sand.y + 1] == 0) {
				sand.y++;
			} else if (grid[sand.x - 1][sand.y + 1] == 0) {
				sand.x--;
				sand.y++;
			} else if (grid[sand.x + 1][sand.y + 1] == 0) {
				sand.x++;
				sand.y++;
			} else {
				isFalling = false;
			}
		}

		grid[sand.x][sand.y] = 'O';
	}
}
