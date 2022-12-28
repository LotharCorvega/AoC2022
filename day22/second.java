package day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class second {
	public static int facing;

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
		facing = 3;

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

			walk(position, instruction.length, grid, width, height);
		}

		System.out.println(1000 * (position.y + 1) + 4 * (position.x + 1) + facing);
	}

	public static final Point[] offsets = {
		new Point( 1,  0), 
		new Point( 0,  1), 
		new Point(-1,  0), 
		new Point( 0, -1)
	};
	
	public static void walk(Point position, int length, char[][] grid, int width, int height) {
		Point next = new Point(position.x, position.y);

		for (int i = 0; i < length; i++) {
			next.x += offsets[facing].x;
			next.y += offsets[facing].y;

			int oldFacing = facing;
			boolean jumped = false;

			if (next.x < 0 || next.x >= width || next.y < 0 || next.y >= height || grid[next.x][next.y] == ' ') {
				jump(next);
				jumped = true;
			}

			if (grid[next.x][next.y] == '#') {
				if (jumped) {
					facing = oldFacing;
				}

				return;
			}

			position.x = next.x;
			position.y = next.y;
		}
	}

	public static void jump(Point position) {
		Point sector = new Point(Math.floorDiv(position.x, 50), Math.floorDiv(position.y, 50));

		if (sector.equals(new Point(1, -1))) {
			position.y = 150 + position.x % 50;
			position.x = 0;
			facing = 0;
			return;
		}

		if (sector.equals(new Point(2, -1))) {
			position.x %= 50;
			position.y = 199;
			facing = 3;
			return;
		}

		if (sector.equals(new Point(0, 0))) {
			position.x = 0;
			position.y = 100 + 49 - position.y;
			facing = 0;
			return;
		}

		if (sector.equals(new Point(3, 0))) {
			position.x = 99;
			position.y = 100 + 49 - position.y;
			facing = 2;
			return;
		}

		if (sector.equals(new Point(0, 1))) {
			if (facing == 2) {
				position.x = position.y % 50;
				position.y = 100;
				facing = 1;
				return;
			}

			if (facing == 3) {
				position.y = position.x + 50;
				position.x = 50;
				facing = 0;
				return;
			}
		}

		if (sector.equals(new Point(2, 1))) {
			if (facing == 0) {
				position.x = position.y % 50 + 100;
				position.y = 49;
				facing = 3;
				return;
			}

			if (facing == 1) {
				position.y = position.x % 50 + 50;
				position.x = 99;
				facing = 2;
				return;
			}
		}

		if (sector.equals(new Point(-1, 2))) {
			position.y = 49 - position.y % 50;
			position.x = 50;
			facing = 0;
			return;
		}

		if (sector.equals(new Point(2, 2))) {
			position.y = 49 - position.y % 50;
			position.x = 149;
			facing = 2;
			return;
		}

		if (sector.equals(new Point(-1, 3))) {
			position.x = 50 + position.y % 50;
			position.y = 0;
			facing = 1;
			return;
		}

		if (sector.equals(new Point(1, 3))) {
			if (facing == 0) {
				position.x = position.y % 50 + 50;
				position.y = 149;
				facing = 3;
				return;
			}

			if (facing == 1) {
				position.y = position.x % 50 + 150;
				position.x = 49;
				facing = 2;
				return;
			}
		}

		if (sector.equals(new Point(0, 4))) {
			position.x = 100 + position.x;
			position.y = 0;
			facing = 1;
			return;
		}

		System.out.println("Error at: (" + position.x + ", " + position.y + ")");
	}

	public static int scanInt(Scanner scanner) {
		String s = "";

		while (scanner.hasNext("\\d")) {
			s += scanner.next("\\d");
		}

		return Integer.parseInt(s);
	}
}
