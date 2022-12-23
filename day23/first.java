package day23;

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
	};

	@Override
	public int hashCode() {
		return this.x + this.y << 16;
	}

	@Override
	public boolean equals(Object o) {
		Point p = (Point) o;
		return this.x == p.x && this.y == p.y;
	}
}

class MovePoint {
	Point position;
	Point destination;

	public MovePoint(Point position, int x, int y) {
		this.position = position;
		this.destination = new Point(x, y);
	}

	@Override
	public int hashCode() {
		return destination.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		MovePoint p = (MovePoint) o;
		return this.destination.equals(p.destination);
	}
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day23/input.txt"));
		Set<Point> elves = new HashSet<>();

		for (int y = 0; scanner.hasNextLine(); y++) {
			String line = scanner.nextLine();

			for (int x = 0; x < line.length(); x++) {
				if (line.charAt(x) == '#') {
					elves.add(new Point(x, y));
				}
			}
		}

		// 0 = N, 1 = S, 2 = W, 3 = E
		boolean[] directions = new boolean[4];
		Point[] offsets = { 
			new Point( 0, -1), 
			new Point( 0,  1), 
			new Point(-1,  0), 
			new Point( 1,  0) 
		};

		for (int i = 0; i < 10; i++) {
			// Set up moved set
			Set<MovePoint> movedElves = new HashSet<>();
			Set<MovePoint> collisions = new HashSet<>();

			// Look for possible steps
			for (Point elve : elves) {
				boolean N = !elves.contains(new Point(elve.x + 0, elve.y - 1));
				boolean S = !elves.contains(new Point(elve.x + 0, elve.y + 1));
				boolean W = !elves.contains(new Point(elve.x - 1, elve.y + 0));
				boolean E = !elves.contains(new Point(elve.x + 1, elve.y + 0));

				boolean NW = !elves.contains(new Point(elve.x - 1, elve.y - 1));
				boolean NE = !elves.contains(new Point(elve.x + 1, elve.y - 1));
				boolean SW = !elves.contains(new Point(elve.x - 1, elve.y + 1));
				boolean SE = !elves.contains(new Point(elve.x + 1, elve.y + 1));

				directions[0] = N && NW && NE;
				directions[1] = S && SW && SE;
				directions[2] = W && NW && SW;
				directions[3] = E && NE && SE;

				if (N && S && W && E && NW && NE && SW && SE) {
					continue;
				} else if (directions[(i + 0) % 4]) {
					MovePoint moveElve = new MovePoint(elve, elve.x + offsets[(i + 0) % 4].x, elve.y + offsets[(i + 0) % 4].y);

					if (!movedElves.contains(moveElve)) {
						movedElves.add(moveElve);
					} else {
						collisions.add(moveElve);
					}
				} else if (directions[(i + 1) % 4]) {
					MovePoint moveElve = new MovePoint(elve, elve.x + offsets[(i + 1) % 4].x, elve.y + offsets[(i + 1) % 4].y);

					if (!movedElves.contains(moveElve)) {
						movedElves.add(moveElve);
					} else {
						collisions.add(moveElve);
					}
				} else if (directions[(i + 2) % 4]) {
					MovePoint moveElve = new MovePoint(elve, elve.x + offsets[(i + 2) % 4].x, elve.y + offsets[(i + 2) % 4].y);

					if (!movedElves.contains(moveElve)) {
						movedElves.add(moveElve);
					} else {
						collisions.add(moveElve);
					}
				} else if (directions[(i + 3) % 4]) {
					MovePoint moveElve = new MovePoint(elve, elve.x + offsets[(i + 3) % 4].x, elve.y + offsets[(i + 3) % 4].y);

					if (!movedElves.contains(moveElve)) {
						movedElves.add(moveElve);
					} else {
						collisions.add(moveElve);
					}
				}
			}

			// Move elves
			for (MovePoint movedElve : movedElves) {
				if (!collisions.contains(movedElve)) {
					elves.remove(movedElve.position);
					elves.add(movedElve.destination);
				}
			}
		}

		System.out.println(countEmpty(elves));
	}

	public static int countEmpty(Set<Point> elves) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (Point elve : elves) {
			minX = Math.min(minX, elve.x);
			maxX = Math.max(maxX, elve.x);
			minY = Math.min(minY, elve.y);
			maxY = Math.max(maxY, elve.y);
		}

		return (maxX - minX + 1) * (maxY - minY + 1) - elves.size();
	}
}
