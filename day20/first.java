package day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class IntNode {
	int value;

	public IntNode(int value) {
		this.value = value;
	}
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day20/input.txt"));
		ArrayList<IntNode> file = new ArrayList<>();

		while (scanner.hasNextInt()) {
			file.add(new IntNode(scanner.nextInt()));
		}

		List<IntNode> mixed = new LinkedList<>(file);
		int n = file.size();

		for (int i = 0; i < n; i++) {
			IntNode currentNode = file.get(i);

			int oldIndex = mixed.indexOf(currentNode);
			int newIndex = -1;

			int value = currentNode.value;

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

		int sum = 0;

		sum += mixed.get(Math.floorMod(zeroIndex + 1000, n)).value;
		sum += mixed.get(Math.floorMod(zeroIndex + 2000, n)).value;
		sum += mixed.get(Math.floorMod(zeroIndex + 3000, n)).value;

		System.out.println(sum);
	}
}
