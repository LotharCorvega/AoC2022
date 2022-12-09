package day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day09/input.txt"));

		int length = 10;
		Point[] rope = new Point[length];
		
		for (int i = 0; i < length; i++) {
			rope[i] = new Point(0, 0);
		}

		Set<Point> visited = new HashSet<>();

		while (scanner.hasNextLine()) {
			String instruction = scanner.nextLine();
			int value = Integer.parseInt(instruction.substring(2));

			switch (instruction.charAt(0)) {
			case 'L':
				for (int i = 0; i < value; i++) {
					rope[0].x--;
					for (int j = 0; j < length - 1; j++) {
						moveTail(rope[j], rope[j + 1]);
					}
					visited.add(new Point(rope[length - 1].x, rope[length - 1].y));
				}
				break;
			case 'R':
				for (int i = 0; i < value; i++) {
					rope[0].x++;
					for (int j = 0; j < length - 1; j++) {
						moveTail(rope[j], rope[j + 1]);
					}
					visited.add(new Point(rope[length - 1].x, rope[length - 1].y));
				}
				break;
			case 'D':
				for (int i = 0; i < value; i++) {
					rope[0].y--;
					for (int j = 0; j < length - 1; j++) {
						moveTail(rope[j], rope[j + 1]);
					}
					visited.add(new Point(rope[length - 1].x, rope[length - 1].y));
				}
				break;
			case 'U':
				for (int i = 0; i < value; i++) {
					rope[0].y++;
					for (int j = 0; j < length - 1; j++) {
						moveTail(rope[j], rope[j + 1]);
					}
					visited.add(new Point(rope[length - 1].x, rope[length - 1].y));
				}
				break;
			}
		}
		
		System.out.println(visited.size());
	}

	public static void moveTail(Point head, Point tail) {
		int dx = head.x - tail.x;
		int dy = head.y - tail.y;

		if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
			return;
		} else if (dx == 0) {
			tail.y += Math.signum(dy);
		} else if (dy == 0) {
			tail.x += Math.signum(dx);
		} else {
			tail.x += Math.signum(dx);
			tail.y += Math.signum(dy);
		}
	}
}
