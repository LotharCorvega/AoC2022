package day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

enum DIRECTION {
	LEFT, RIGHT, UP, DOWN
}

class State implements Comparable<State> {
	static int cycle;

	int x;
	int y;
	int minutes;

	public State(int x, int y, int minutes) {
		this.x = x;
		this.y = y;
		this.minutes = minutes;
	}

	@Override
	public int compareTo(State state) {
		return this.minutes - state.minutes;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + x;
		hash = hash * 31 + y;
		hash = hash * 31 + minutes % cycle;
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		State state = (State) o;
		return this.x == state.x && this.y == state.y && this.minutes % cycle == state.minutes % cycle;
	}
}

class Blizzard {
	int x;
	int y;
	DIRECTION direction;

	public Blizzard(int x, int y, DIRECTION direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
}

public class second {
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

		int way0 = walk(new State(0, 0, 1), new State(width - 1, height - 1, 0), width, height, cycle, valley);
		int way1 = walk(new State(width - 1, height - 1, way0 + 2), new State(0, 0, 0), width, height, cycle, valley);
		int way2 = walk(new State(0, 0, way1 + 2), new State(width - 1, height - 1, 0), width, height, cycle, valley);

		System.out.println(way2 + 1);
	}

	public static int walk(State start, State end, int width, int height, int cycle, boolean[][][] valley) {
		PriorityQueue<State> states = new PriorityQueue<>();
		Set<State> visited = new HashSet<>();

		for (int i = start.minutes; i < start.minutes + cycle; i++) {
			if (!valley[start.x][start.y][i % cycle]) {
				State state = new State(start.x, start.y, i);
				states.add(state);
				visited.add(state);
			}
		}

		while (!states.isEmpty()) {
			State c = states.poll();

			// Cycle
			if (c.x == end.x && c.y == end.y) {
				return c.minutes;
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

		return -1;
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
