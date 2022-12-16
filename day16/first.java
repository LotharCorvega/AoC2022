package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day16/input.txt"));

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split("(Valve )|( has flow rate=)|(; tunnels? leads? to valves? )|(, )");
		}
	}
}
