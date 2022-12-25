package day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class first {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day25/input.txt"));
		ArrayList<String> input = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			input.add(scanner.nextLine());
		}

		long sum = 0;

		for (String s : input) {
			sum += SNAFUToDecimal(s);
		}

		System.out.println(decimalToSNAFU(sum));
	}

	public static long SNAFUToDecimal(String s) {
		int length = s.length();
		long result = 0;

		for (int i = 0; i < length; i++) {
			char c = s.charAt(length - (i + 1));
			long pow = (long) Math.pow(5, i);

			switch (c) {
			case '=':
				result -= 2 * pow;
				break;
			case '-':
				result -= pow;
				break;
			case '0':
				break;
			case '1':
				result += pow;
				break;
			case '2':
				result += 2 * pow;
				break;
			}
		}

		return result;
	}

	public static String decimalToSNAFU(long a) {
		String result = "";

		int length = (int) (Math.log(a) / Math.log(5));

		if (maxValue(length + 1) < a) {
			length++;
		}

		for (int order = length; order >= 0; order--) {
			long subtractor = (long) Math.pow(5, order);
			long difference = a + ((a < 0) ? -maxValue(order) : maxValue(order));

			int digit = (int) (difference / subtractor);
			a -= digit * subtractor;

			switch (digit) {
			case -2:
				result += "=";
				break;
			case -1:
				result += "-";
				break;
			case 0:
				result += "0";
				break;
			case 1:
				result += "1";
				break;
			case 2:
				result += "2";
				break;
			default:
				System.out.println("Error: " + digit);
			}
		}

		return result;
	}

	public static long maxValue(int digits) {
		long result = 0;

		for (int i = 0; i < digits; i++) {
			result += (long) Math.pow(5, i);
		}

		return result * 2;
	}
}
