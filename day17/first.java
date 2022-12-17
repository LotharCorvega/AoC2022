package day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Rock {
	Point[] body;

	int width;
	int height;

	public Rock(Point[] body, int width, int height) {
		this.body = body;
		this.width = width;
		this.height = height;
	}
}

public class first {
	static final int width = 7;	
	
	static final Point[] body0 = { new Point(0,0), new Point(1,0), new Point(2, 0), new Point(3,0) };
	static final Point[] body1 = { new Point(1,0), new Point(0,1), new Point(1, 1), new Point(2,1), new Point(1,2) };
	static final Point[] body2 = { new Point(2,0), new Point(2,1), new Point(0, 2), new Point(1,2), new Point(2,2) };
	static final Point[] body3 = { new Point(0,0), new Point(0,1), new Point(0, 2), new Point(0,3) };
	static final Point[] body4 = { new Point(0,0), new Point(1,0), new Point(0, 1), new Point(1,1) };
	
	static final Rock[] rocks = {
		new Rock( body0, 4, 1),
		new Rock( body1, 3, 3),
		new Rock( body2, 3, 3),
		new Rock( body3, 1, 4),
		new Rock( body4, 2, 2)
	};
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day17/input.txt"));
		String input = scanner.nextLine();
		scanner.close();

		int inputIndex = 0;
		int height = 0;

		LinkedList<char[]> grid = new LinkedList<char[]>();

		// Initialize grid
		for (int i = 0; i < 8; i++) {
			grid.add(new char[width]);
		}

		// Set the floor
		grid.set(7, new char[] { '#', '#', '#', '#', '#', '#', '#', });

		// Main loop
		for (int i = 0; i < 2022; i++) {
			Rock currentRock = rocks[i % 5];
			Point rockPosition = new Point(2, 4 - currentRock.height);

			while (true) {
				// Side thrusters
				int direction = (input.charAt(inputIndex) == '>') ? 1 : -1;
				if (!collide(grid, currentRock, rockPosition.x + direction, rockPosition.y)) {
					rockPosition.x += direction;
				}

				// Increment index
				inputIndex = (inputIndex + 1) % input.length();

				// Fall
				if (!collide(grid, currentRock, rockPosition.x, rockPosition.y + 1)) {
					rockPosition.y++;
				} else {
					break;
				}
			}

			// Set rock to rest
			for (Point rockPart : currentRock.body) {
				grid.get(rockPart.y + rockPosition.y)[rockPart.x + rockPosition.x] = '#';
			}

			// Determine height gain
			int heightGain = 0;

			for (int y = 0; y < grid.size(); y++) {
				boolean found = false;
				for (int x = 0; x < width; x++) {
					if (grid.get(y)[x] != 0) {
						found = true;
						break;
					}
				}
				if (found) {
					heightGain = 7 - y;
					break;
				}
			}

			// Add necessary space above
			for (int j = 0; j < heightGain; j++) {
				grid.addFirst(new char[width]);
			}
			
			height += heightGain;
		}

		System.out.println(height);
	}

	public static boolean collide(List<char[]> grid, Rock rock, int offsetX, int offsetY) {
		for (Point rockPart : rock.body) {
			int x = rockPart.x + offsetX;
			int y = rockPart.y + offsetY;

			if (x < 0 || x >= width || grid.get(y)[x] != 0) {
				return true;
			}
		}

		return false;
	}
}
