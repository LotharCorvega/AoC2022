package day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Point {
	int x;
	int y;
	int z;

	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int hashCode() {
		return x + (y << 10) + (z << 20);
	}

	@Override
	public boolean equals(Object o) {
		Point p = (Point) o;
		return this.x == p.x && this.y == p.y && this.z == p.z;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day18/input.txt"));

		Set<Point> cubes = new HashSet<>();

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(",");

			int x = Integer.parseInt(line[0]);
			int y = Integer.parseInt(line[1]);
			int z = Integer.parseInt(line[2]);

			Point p = new Point(x, y, z);
			cubes.add(p);
		}

		scanner.close();
		System.out.println(getSurface(cubes));
	}
	
	final static Point[] offsets = {
		new Point( 0,  0, -1),
		new Point( 0,  0,  1),
		new Point( 0, -1,  0),
		new Point( 0,  1,  0),
		new Point(-1,  0,  0),
		new Point( 1,  0,  0)	
	};

	public static int getSurface(Set<Point> cubes) {
		int surfaceArea = 0;

		for (Point p : cubes) {
			for (Point offset : offsets) {
				Point neighbor = new Point(p.x + offset.x, p.y + offset.y, p.z + offset.z);

				if (!cubes.contains(neighbor)) {
					surfaceArea++;
				}
			}
		}

		return surfaceArea;
	}
}
