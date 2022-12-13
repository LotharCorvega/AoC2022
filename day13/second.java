package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day13/input.txt")).useDelimiter("");
		ArrayList<PacketNode> packets = new ArrayList<>();

		while (scanner.hasNextLine()) {
			packets.add(PacketNode.readPacket(scanner));
			scanner.nextLine();
			packets.add(PacketNode.readPacket(scanner));
			scanner.nextLine();

			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		}

		// maybe optimise
		PacketNode two = new PacketNode();
		PacketNode six = new PacketNode();

		two.list.add(new PacketNode());
		six.list.add(new PacketNode());

		two.list.get(0).list.add(new IntNode(2));
		six.list.get(0).list.add(new IntNode(6));

		packets.add(two);
		packets.add(six);

		Collections.sort(packets);

		int indexTwo = 0;
		int indexSix = 0;

		for (int i = 0; i < packets.size(); i++) {
			PacketNode currentPacket = packets.get(i);

			if (currentPacket == two) {
				indexTwo = i + 1;
			} else if (currentPacket == six) {
				indexSix = i + 1;
			}
		}

		System.out.println(indexTwo * indexSix);
	}
}
