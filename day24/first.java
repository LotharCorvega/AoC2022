package day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day24/input.txt"));
		String first = scanner.nextLine();
		int width = first.length() - 2;
		int height = 0;

		ArrayList<Blizzard> blizzards = new ArrayList<>();

		while (true) {
			String line = scanner.nextLine();

			if (line.charAt(1) == '#') {
				break;
			}

			for (int x = 0; x < width; x++) {
				switch (line.charAt(x + 1)) {
				case '<':
					blizzards.add(new Blizzard(x, height, DIRECTION.LEFT));
					break;
				case '>':
					blizzards.add(new Blizzard(x, height, DIRECTION.RIGHT));
					break;
				case '^':
					blizzards.add(new Blizzard(x, height, DIRECTION.UP));
					break;
				case 'v':
					blizzards.add(new Blizzard(x, height, DIRECTION.DOWN));
					break;
				}
			}

			height++;
		}

		int cycle = lcm(width, height);
		State.cycle = cycle;

		boolean[][][] valley = simulate(width, height, cycle, blizzards);

		PriorityQueue<State> states = new PriorityQueue<>();
		Set<State> visited = new HashSet<>();

		for (int i = 0; i < cycle; i++) {
			if (!valley[0][0][i]) {
				State state = new State(0, 0, i);
				states.add(state);
				visited.add(state);
			}
		}

		while (!states.isEmpty()) {
			State c = states.poll();

			// Cycle
			if (c.x == width - 1 && c.y == height - 1) {
				System.out.println(c.minutes + 1);
				break;
			}

			// Stay
			if (!valley[c.x][c.y][(c.minutes + 1) % cycle]) {
				State state = new State(c.x, c.y, c.minutes + 1);

				if (!visited.contains(state)) {
					visited.add(state);
					states.add(state);
				}
			}

			// Left
			if (c.x > 0 && !valley[c.x - 1][c.y][(c.minutes + 1) % cycle]) {
				State state = new State(c.x - 1, c.y, c.minutes + 1);

				if (!visited.contains(state)) {
					visited.add(state);
					states.add(state);
				}
			}

			// Right
			if (c.x < width - 1 && !valley[c.x + 1][c.y][(c.minutes + 1) % cycle]) {
				State state = new State(c.x + 1, c.y, c.minutes + 1);

				if (!visited.contains(state)) {
					visited.add(state);
					states.add(state);
				}
			}

			// UP
			if (c.y > 0 && !valley[c.x][c.y - 1][(c.minutes + 1) % cycle]) {
				State state = new State(c.x, c.y - 1, c.minutes + 1);

				if (!visited.contains(state)) {
					visited.add(state);
					states.add(state);
				}
			}

			// Right
			if (c.y < height - 1 && !valley[c.x][c.y + 1][(c.minutes + 1) % cycle]) {
				State state = new State(c.x, c.y + 1, c.minutes + 1);

				if (!visited.contains(state)) {
					visited.add(state);
					states.add(state);
				}
			}
		}
	}

	public static boolean[][][] simulate(int width, int height, int cycle, ArrayList<Blizzard> blizzards) {
		boolean[][][] valley = new boolean[width][height][cycle];

		for (int i = 0; i < cycle; i++) {
			for (Blizzard blizzard : blizzards) {
				valley[blizzard.x][blizzard.y][i] = true;

				switch (blizzard.direction) {
				case LEFT:
					blizzard.x = Math.floorMod(blizzard.x - 1, width);
					break;
				case RIGHT:
					blizzard.x = Math.floorMod(blizzard.x + 1, width);
					break;
				case UP:
					blizzard.y = Math.floorMod(blizzard.y - 1, height);
					break;
				case DOWN:
					blizzard.y = Math.floorMod(blizzard.y + 1, height);
					break;
				}
			}
		}

		return valley;
	}

	public static int lcm(int a, int b) {
		return (a * b) / gcd(a, b);
	}

	public static int gcd(int a, int b) {
		if (a == 0) {
			return b;
		} else {
			return gcd(b % a, a);
		}
	}
}
