package day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class LongNode {
	long value;

	public LongNode(long value) {
		this.value = value;
	}
}

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day20/input.txt"));
		ArrayList<LongNode> file = new ArrayList<>();

		while (scanner.hasNextLong()) {
			file.add(new LongNode(scanner.nextLong() * 811589153L));
		}

		List<LongNode> mixed = new LinkedList<>(file);
		int n = file.size();

		for (int i = 0; i < n * 10; i++) {
			LongNode currentNode = file.get(i % n);

			int oldIndex = mixed.indexOf(currentNode);
			int newIndex = -1;

			long value = currentNode.value;

			// Idek
			if (value > 0) {
				newIndex = Math.floorMod(oldIndex + 1 + value + value / (n - 1), n);
			} else if (value < 0) {
				int invIndex = (n - 1) - oldIndex;
				invIndex = Math.floorMod(invIndex + 1 - value - value / (n - 1), n);
				newIndex = (n - 1) - invIndex + 1;
			} else {
				newIndex = oldIndex;
			}

			if (newIndex <= oldIndex) {
				oldIndex++;
			}

			mixed.add(newIndex, currentNode);
			mixed.remove(oldIndex);
		}

		int zeroIndex = -1;

		for (int i = 0; i < n; i++) {
			if (mixed.get(i).value == 0) {
				zeroIndex = i;
				break;
			}
		}

		long sum = 0;

		sum += mixed.get(Math.floorMod(zeroIndex + 1000, n)).value;
		sum += mixed.get(Math.floorMod(zeroIndex + 2000, n)).value;
		sum += mixed.get(Math.floorMod(zeroIndex + 3000, n)).value;

		System.out.println(sum);
	}
}
