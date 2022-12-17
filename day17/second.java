package day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class State {
	int currentRock;
	int inputIndex;
	int[] surface;

	public State(int currentRock, int inputIndex, int[] surface) {
		this.currentRock = currentRock;
		this.inputIndex = inputIndex;
		this.surface = surface;
	}

	@Override
	public boolean equals(Object o) {
		return this.currentRock == ((State) o).currentRock && this.inputIndex == ((State) o).inputIndex
				&& Arrays.equals(this.surface, ((State) o).surface);
	}
}

public class second {
	static final int width = 7;	
	
	static final Point[] body0 = { new Point(0,0), new Point(1,0), new Point(2, 0), new Point(3,0) };
	static final Point[] body1 = { new Point(1,0), new Point(0,1), new Point(1, 1), new Point(2,1), new Point(1,2) };
	static final Point[] body2 = { new Point(2,0), new Point(2,1), new Point(0, 2), new Point(1,2), new Point(2,2) };
	static final Point[] body3 = { new Point(0,0), new Point(0,1), new Point(0, 2), new Point(0,3) };
	static final Point[] body4 = { new Point(0,0), new Point(1,0), new Point(0, 1), new Point(1,1) };
	
	static final Rock[] rocks = {
		new Rock( body0, 4, 1),
		new Rock( body1, 3, 3),
		new Rock( body2, 3, 3),
		new Rock( body3, 1, 4),
		new Rock( body4, 2, 2)
	};

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day17/input.txt"));
		String input = scanner.nextLine();
		scanner.close();

		LinkedList<char[]> grid = new LinkedList<char[]>();

		// Initialize grid
		for (int i = 0; i < 8; i++) {
			grid.add(new char[] { ' ', ' ', ' ', ' ', ' ', ' ', ' ', });
		}

		// Set the floor
		grid.set(7, new char[] { '#', '#', '#', '#', '#', '#', '#', });

		ArrayList<State> states = new ArrayList<>();
		ArrayList<Integer> heights = new ArrayList<>();

		int rockCount = 0;
		int inputIndex = 0;

		int loopOffset = 0;
		int height = 0;

		// Main loop
		while (true) {
			Rock currentRock = rocks[rockCount % 5];
			Point rockPosition = new Point(2, 4 - currentRock.height);

			while (true) {
				// Side thrusters
				int direction = (input.charAt(inputIndex) == '>') ? 1 : -1;
				if (!collide(grid, currentRock, rockPosition.x + direction, rockPosition.y)) {
					rockPosition.x += direction;
				}

				// Increment index
				inputIndex = (inputIndex + 1) % input.length();

				// Fall
				if (!collide(grid, currentRock, rockPosition.x, rockPosition.y + 1)) {
					rockPosition.y++;
				} else {
					break;
				}
			}

			// Set rock to rest
			for (Point rockPart : currentRock.body) {
				grid.get(rockPart.y + rockPosition.y)[rockPart.x + rockPosition.x] = (char) ('#' + rockCount % 5);
			}

			// Determine height gain
			int heightGain = 0;

			for (int y = 0; y < grid.size(); y++) {
				boolean found = false;
				for (int x = 0; x < width; x++) {
					if (grid.get(y)[x] != ' ') {
						found = true;
						break;
					}
				}
				if (found) {
					heightGain = 7 - y;
					break;
				}
			}

			// Add necessary space above
			for (int j = 0; j < heightGain; j++) {
				grid.addFirst(new char[] { ' ', ' ', ' ', ' ', ' ', ' ', ' ', });
			}

			// Check for reoccuring states
			State currentState = new State(rockCount % 5, inputIndex, getSurface(grid));

			if (states.contains(currentState)) {
				loopOffset = states.indexOf(currentState);
				break;
			} else {
				states.add(currentState);
			}

			height += heightGain;
			heights.add(height);

			rockCount++;
		}

		int loopLength = rockCount - loopOffset;
		int loopHeight = heights.get(rockCount - 1) - heights.get(loopOffset - 1);

		long loops = (1000000000000L - loopOffset) / (long) loopLength;
		long rest = 1000000000000L - loops * loopLength;

		System.out.println(loopHeight * loops + heights.get((int) (rest - 1)));
	}

	public static boolean collide(List<char[]> grid, Rock rock, int offsetX, int offsetY) {
		for (Point rockPart : rock.body) {
			int x = rockPart.x + offsetX;
			int y = rockPart.y + offsetY;

			if (x < 0 || x >= width || grid.get(y)[x] != ' ') {
				return true;
			}
		}

		return false;
	}

	public static int[] getSurface(List<char[]> grid) {
		int[] surface = new int[7];

		for (int x = 0; x < 7; x++) {
			int y = 0;

			while (grid.get(y)[x] == ' ') {
				y++;
			}

			surface[x] = y;
		}

		return surface;
	}
}
