package day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		Point p = (Point)o;		
		return this.x == p.x && this.y == p.y;
	}
}

class Instruction {
	boolean direction; // L = 0, R = 1
	int length;

	public Instruction(boolean direction, int length) {
		this.direction = direction;
		this.length = length;
	}
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day22/input.txt")).useDelimiter("");
		ArrayList<String> input = new ArrayList<>();
		ArrayList<Instruction> instructions = new ArrayList<>();

		int width = 0;
		int height = 0;

		// read grid
		while (true) {
			String line = scanner.nextLine();

			if (line.isEmpty()) {
				height = input.size();
				break;
			}

			input.add(line);
			width = Math.max(width, line.length());
		}

		// first instruction
		instructions.add(new Instruction(true, scanInt(scanner)));

		// read instructions
		while (scanner.hasNext()) {
			boolean direction = scanner.next().equals("R");
			instructions.add(new Instruction(direction, scanInt(scanner)));
		}

		// generate grid
		char[][] grid = new char[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x < input.get(y).length()) {
					grid[x][y] = input.get(y).charAt(x);
				} else {
					grid[x][y] = ' ';
				}
			}
		}

		Point position = new Point(0, 0);
		int facing = 3;

		while (grid[position.x][position.y] == ' ') {
			position.x++;
		}

		// Execute instructions
		for (Instruction instruction : instructions) {
			if (instruction.direction) {
				facing = Math.floorMod(facing + 1, 4);
			} else {
				facing = Math.floorMod(facing - 1, 4);
			}

			walk(position, facing, instruction.length, grid, width, height);
		}

		System.out.println(1000 * (position.y + 1) + 4 * (position.x + 1) + facing);
	}

	public static final Point[] offsets = {
		new Point( 1,  0), 
		new Point( 0,  1), 
		new Point(-1,  0), 
		new Point( 0, -1)
	};

	public static void walk(Point position, int facing, int length, char[][] grid, int width, int height) {
		Point next = new Point(position.x, position.y);

		for (int i = 0; i < length; i++) {
			next.x += offsets[facing].x;
			next.y += offsets[facing].y;

			if (next.x < 0 || next.x >= width) {
				next.x -= offsets[facing].x;

				do {
					next.x -= offsets[facing].x;
				} while (next.x > 0 && next.x < width - 1 && grid[next.x - offsets[facing].x][next.y] != ' ');
			} else if (next.y < 0 || next.y >= height) {
				next.y -= offsets[facing].y;

				do {
					next.y -= offsets[facing].y;
				} while (next.y > 0 && next.y < height - 1 && grid[next.x][next.y - offsets[facing].y] != ' ');
			} else if (grid[next.x][next.y] == ' ') {
				next.x -= offsets[facing].x;
				next.y -= offsets[facing].y;

				do {
					next.x -= offsets[facing].x;
					next.y -= offsets[facing].y;
				} while (next.x > 0 && next.x < width - 1 && next.y > 0 && next.y < height - 1 && grid[next.x - offsets[facing].x][next.y - offsets[facing].y] != ' ');
			}

			if (grid[next.x][next.y] == '#') {
				return;
			}

			position.x = next.x;
			position.y = next.y;
		}
	}

	public static int scanInt(Scanner scanner) {
		String s = "";

		while (scanner.hasNext("\\d")) {
			s += scanner.next("\\d");
		}

		return Integer.parseInt(s);
	}
}
