package day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Dir {
	String name;
	Dir parent;

	Set<Dir> subDirectories;
	Set<SFile> files;

	public Dir(String name, Dir parent) {
		this.name = name;
		this.parent = parent;

		subDirectories = new HashSet<>();
		files = new HashSet<>();
	}

	public int getSize() {
		int size = 0;

		for (Dir subDir : subDirectories) {
			size += subDir.getSize();
		}

		for (SFile file : files) {
			size += file.size;
		}

		return size;
	}
}

class SFile {
	String name;
	int size;

	public SFile(String name, int size) {
		this.name = name;
		this.size = size;
	}
}

public class first {
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

		int sum = 0;

		for (Dir dir : directories) {
			int size = dir.getSize();

			if (size <= 100000) {
				sum += size;
			}
		}

		System.out.println(sum);
	}
}
