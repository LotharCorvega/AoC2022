package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day12/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}

		int width = input.get(0).length();
		int height = input.size();

		char[][] grid = new char[width][height];
		int[][] dist = new int[width][height];

		Queue<Point> Q = new LinkedList<>();
		Point end = new Point(0, 0);

		for (int y = 0; y < height; y++) {
			String currentLine = input.get(y);
			for (int x = 0; x < width; x++) {
				char c = currentLine.charAt(x);

				if (c == 'S' || c == 'a') {
					dist[x][y] = 0;
					Q.add(new Point(x, y));

					grid[x][y] = 'a';
				} else if (c == 'E') {
					end.x = x;
					end.y = y;

					grid[x][y] = 'z';
					dist[x][y] = -1;
				} else {
					grid[x][y] = c;
					dist[x][y] = -1;
				}
			}
		}

		Point[] offsets = { new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1) };

		while (!Q.isEmpty()) {
			Point u = Q.poll();

			for (Point offset : offsets) {
				Point v = new Point(u.x + offset.x, u.y + offset.y);

				if (v.x >= 0 && v.x < width && v.y >= 0 && v.y < height) {
					if (dist[v.x][v.y] == -1 && grid[v.x][v.y] - grid[u.x][u.y] <= 1) {

						dist[v.x][v.y] = dist[u.x][u.y] + 1;
						Q.add(v);
					}
				}
			}
		}

		System.out.println(dist[end.x][end.y]);
	}
}
