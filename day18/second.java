package day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day18/input.txt"));

		Set<Point> cubes = new HashSet<>();

		Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(",");

			int x = Integer.parseInt(line[0]);
			int y = Integer.parseInt(line[1]);
			int z = Integer.parseInt(line[2]);

			Point p = new Point(x, y, z);
			cubes.add(p);

			// idek
			if (x < min.x) {
				min.x = x;
			}

			if (y < min.y) {
				min.y = y;
			}

			if (z < min.z) {
				min.z = z;
			}

			if (x > max.x) {
				max.x = x;
			}

			if (y > max.y) {
				max.y = y;
			}

			if (z > max.z) {
				max.z = z;
			}
		}

		Set<Point> complement = getComplement(cubes, min, max);
		int surfaceArea = 0;

		for (Point iterator : complement) {
			surfaceArea += touching(cubes, iterator);
		}

		scanner.close();
		System.out.println(surfaceArea);
	}

	final static Point[] offsetsOrthogonal = {
		new Point( 0,  0, -1),
		new Point( 0,  0,  1),
		new Point( 0, -1,  0),
		new Point( 0,  1,  0),
		new Point(-1,  0,  0),
		new Point( 1,  0,  0)	
	};
	
	public static int touching(Set<Point> cubes, Point test) {
		int count = 0;

		for (Point offset : offsetsOrthogonal) {
			Point neighbor = new Point(test.x + offset.x, test.y + offset.y, test.z + offset.z);

			if (cubes.contains(neighbor)) {
				count++;
			}
		}

		return count;
	}

	public static Set<Point> getComplement(Set<Point> cubes, Point min, Point max) {
		Set<Point> complement = new HashSet<>();
		complement.add(min);

		Stack<Point> Q = new Stack<>();
		Q.push(min);

		while (!Q.isEmpty()) {
			Point current = Q.pop();

			for (Point offset : offsetsOrthogonal) {
				Point neighbor = new Point(current.x + offset.x, current.y + offset.y, current.z + offset.z);

				if (inBound(neighbor, min, max) && !complement.contains(neighbor) && !cubes.contains(neighbor)) {
					complement.add(neighbor);
					Q.push(neighbor);
				}
			}
		}

		return complement;
	}

	public static boolean inBound(Point p, Point min, Point max) {
		return
			p.x >= min.x - 1 && p.x <= max.x + 1 &&
			p.y >= min.y - 1 && p.y <= max.y + 1 &&
			p.z >= min.z - 1 && p.z <= max.z + 1;
	}
}
