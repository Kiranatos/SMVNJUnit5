package net.kiranatos.legacyjunit4;

public class Main {

	public static int add(int a, int b) {
		return a + b;
	}

	public static int get(int index) {
		int[] m = new int[] { 10, 11 };
		return m[index];
	}

	public static void main(String[] args) {
		System.out.println(add(2, 3));
		System.out.println(get(11));
	}
}

/* лог от 20.12.2019 Подключил jar-ники: hamcrest-core-1.3.jar и
 * junit-4.13-rc-2.jar, чтоб проект пока не матюкался
 * 
 * заглянуть в ссылки: https://junit.org/junit5/
 * https://junit.org/junit5/docs/current/api/
 * https://www.guru99.com/download-installation-junit.html
 * https://github.com/junit-team/junit4/wiki/Download-and-Install
 */