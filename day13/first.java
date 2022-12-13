package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class PacketNode implements Comparable<PacketNode> {
	List<PacketNode> list;

	public PacketNode() {
		this.list = new LinkedList<>();
	}

	public static PacketNode readPacket(Scanner scanner) {
		PacketNode result = new PacketNode();
		scanner.next("\\[");

		while (scanner.hasNext("[0-9]") || scanner.hasNext("\\[")) {
			if (scanner.hasNext("[0-9]")) {
				String s = "";

				while (scanner.hasNext("[0-9]")) {
					s += scanner.next();
				}

				result.list.add(new IntNode(Integer.parseInt(s)));
			} else {
				result.list.add(readPacket(scanner));
			}

			if (scanner.hasNext(",")) {
				scanner.next(",");
			}
		}

		scanner.next("\\]");
		return result;
	}

	@Override
	public int compareTo(PacketNode node) {
		if (this instanceof IntNode && node instanceof IntNode) {
			return ((IntNode) this).value - ((IntNode) node).value;
		} else if (this instanceof IntNode) {
			PacketNode newLeft = new PacketNode();
			newLeft.list.add(this);

			return newLeft.compareTo(node);
		} else if (node instanceof IntNode) {
			PacketNode newRight = new PacketNode();
			newRight.list.add(node);

			return this.compareTo(newRight);
		} else {
			for (int i = 0; i < this.list.size() && i < node.list.size(); i++) {
				int comp = this.list.get(i).compareTo(node.list.get(i));

				if (comp != 0) {
					return comp;
				}
			}

			return this.list.size() - node.list.size();
		}
	}
}

class IntNode extends PacketNode {
	int value;

	public IntNode(int value) {
		this.value = value;
	}
}

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day13/input.txt")).useDelimiter(""); // mmh
		int sum = 0;

		for (int i = 1; scanner.hasNextLine(); i++) {
			PacketNode first = PacketNode.readPacket(scanner);
			scanner.nextLine();
			PacketNode second = PacketNode.readPacket(scanner);
			scanner.nextLine();

			if (first.compareTo(second) < 0) {
				sum += i;
			}

			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		}

		System.out.println(sum);
	}
}
