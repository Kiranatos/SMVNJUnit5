package net.kiranatos.examples;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class MathOperations {
	private int result;

	public MathOperations(int innerNum) {
		this.result = innerNum;
	}

	public static int add(int... numbers) {
		return Arrays.stream(numbers).sum();
	}

	public MathOperations add(int number) {
		result += number;
		return this;
	}

	public int getResult() {
		return result;
	}

	public String getString(String str) {
		return str + "пор";
	}

	public static String getExceptions(String exception) {
		String nullExc = null;
		switch (exception) {
		case ("ArithmeticException"):
			return String.valueOf(1 / 0);
		case ("NullPointerException"):
			return nullExc.equals(exception) ? "true" : "false";
		default:
			return "correct string";
		}
	}

	public static void main(String[] args) {
		System.out.println(MathOperations.add(10, 22, 30));
		System.out.println(MathOperations.add());
		System.out.println(MathOperations.add(0, 2));

		MathOperations math = new MathOperations(100);
		System.out.println(math.add(20).add(1000).getResult());
	}

	@Override
	public String toString() {
		return "MathOperations{" + result + '}';
	}
}
