package day23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class second {
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
		
		int i = 0;

		while (true) {
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
			
			if (movedElves.size() - collisions.size() == 0) {
				break;
			}

			// Move elves
			for (MovePoint movedElve : movedElves) {
				if (!collisions.contains(movedElve)) {
					elves.remove(movedElve.position);
					elves.add(movedElve.destination);
				}
			}
			
			i++;
		}

		System.out.println(i + 1);
	}
}
