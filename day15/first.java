package day15;

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
}

class Sensor {
	Point position;
	int range;

	public Sensor(Point position, Point beacon) {
		this.position = position;
		this.range = getDistance(position, beacon);
	}

	public boolean inRange(Point p) {
		return getDistance(position, p) <= range;
	}

	public static int getDistance(Point p1, Point p2) {
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day15/input.txt"));
		ArrayList<Sensor> sensors = new ArrayList<>();
		ArrayList<Point> beacons = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split("=|,|:");

			Point sensorPos = new Point(Integer.parseInt(line[1]), Integer.parseInt(line[3]));
			Point beaconPos = new Point(Integer.parseInt(line[5]), Integer.parseInt(line[7]));

			sensors.add(new Sensor(sensorPos, beaconPos));
			beacons.add(beaconPos);
		}

		int xMin = Integer.MAX_VALUE;
		int xMax = Integer.MIN_VALUE;

		for (Sensor sensor : sensors) {
			if (sensor.position.x - sensor.range < xMin) {
				xMin = sensor.position.x - sensor.range;
			}

			if (sensor.position.x + sensor.range > xMax) {
				xMax = sensor.position.x + sensor.range;
			}
		}

		Point iter = new Point(0, 2000000);
		int count = 0;

		for (iter.x = xMin; iter.x <= xMax; iter.x++) {
			for (Sensor sensor : sensors) {
				if (sensor.inRange(iter)) {
					boolean isBeaconLocation = false;

					for (Point beacon : beacons) {
						if (iter.x == beacon.x && iter.y == beacon.y) {
							isBeaconLocation = true;
							break;
						}
					}

					if (!isBeaconLocation) {
						count++;
						break;
					}
				}
			}
		}

		System.out.println(count);
	}
}
