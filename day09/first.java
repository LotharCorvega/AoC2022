package day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return this.x + this.y << 16;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Point)) {
			return false;
		}

		Point p = (Point) o;
		return this.x == p.x && this.y == p.y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day09/input.txt"));

		Point head = new Point(0, 0);
		Point tail = new Point(0, 0);

		Set<Point> visited = new HashSet<>();

		while (scanner.hasNextLine()) {
			String instruction = scanner.nextLine();
			int value = Integer.parseInt(instruction.substring(2));

			switch (instruction.charAt(0)) {
			case 'L':
				for (int i = 0; i < value; i++) {
					head.x--;
					moveTail(head, tail);
					visited.add(new Point(tail.x, tail.y));
				}
				break;
			case 'R':
				for (int i = 0; i < value; i++) {
					head.x++;
					moveTail(head, tail);
					visited.add(new Point(tail.x, tail.y));
				}
				break;
			case 'D':
				for (int i = 0; i < value; i++) {
					head.y--;
					moveTail(head, tail);
					visited.add(new Point(tail.x, tail.y));
				}
				break;
			case 'U':
				for (int i = 0; i < value; i++) {
					head.y++;
					moveTail(head, tail);
					visited.add(new Point(tail.x, tail.y));
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
