package day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class second {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day07/input.txt"));

		Set<Dir> directories = new HashSet<>();

		Dir root = new Dir("/", null);
		scanner.nextLine(); // $ cd /

		Dir current = root;

		while (scanner.hasNext()) {
			if (scanner.hasNext("\\$")) { // command
				scanner.next(); // $

				if (scanner.hasNext("cd")) {
					scanner.next(); // cd

					String dirName = scanner.next();

					if (dirName.equals("..")) {
						current = current.parent;
					} else {
						for (Dir subDir : current.subDirectories) {
							if (subDir.name.equals(dirName)) {
								current = subDir;
								break;
							}
						}
					}
				} else {
					scanner.next(); // ls
					// dont do anything here
				}
			} else if (scanner.hasNext("dir")) { // dir
				scanner.next(); // dir

				String dirName = scanner.next();

				Dir newDir = new Dir(dirName, current);
				current.subDirectories.add(newDir);
				directories.add(newDir);
			} else { // file
				int fileSize = scanner.nextInt();
				String fileName = scanner.next();

				SFile newFile = new SFile(fileName, fileSize);
				current.files.add(newFile);
			}
		}

		Dir best = root;
		int diff = best.getSize() - 40000000;

		for (Dir dir : directories) {
			int size = dir.getSize();

			if (size < best.getSize() && size > diff) {
				best = dir;
			}
		}

		System.out.println(best.getSize());
	}
}
