package day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day04/input.txt"));
		ArrayList<int[]> input = new ArrayList<int[]>();

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split("[-,]");
			
			int[] nums = new int[4];			
			nums[0] = Integer.parseInt(line[0]);
			nums[1] = Integer.parseInt(line[1]);
			nums[2] = Integer.parseInt(line[2]);
			nums[3] = Integer.parseInt(line[3]);
			
			input.add(nums);
		}
		
		scanner.close();
		
		int count = 0;;
		
		for (int [] range : input) {
			if (range[0] >= range[2] && range[1] <= range[3]) {
				count++;
			} else if (range[0] <= range[2] && range[1] >= range[3]) {
				count++;
			}
		}
		
		System.out.println(count);
	}
}
