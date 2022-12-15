package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class second {
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

		Point iterator = new Point(0, 0);

		for (; iterator.y <= 4000000; iterator.y++) {
			iterator.x = 0;

			while (jump(sensors, iterator));

			if (iterator.x <= 4000000) {
				System.out.println((long) iterator.x * 4000000 + iterator.y);
				return;
			}
		}
	}

	public static boolean jump(ArrayList<Sensor> sensors, Point p) {
		for (Sensor sensor : sensors) {
			if (sensor.inRange(p)) {
				p.x = 1 + sensor.position.x + sensor.range - Math.abs(p.y - sensor.position.y);
				return true;
			}
		}

		return false;
	}
}
