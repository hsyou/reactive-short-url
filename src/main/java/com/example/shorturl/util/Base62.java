package com.example.shorturl.util;

public class Base62 {

	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static final int BASE = ALPHABET.length();

	private Base62() {}

	public static String toBase62(String longURL) {
		long n = 0;
		char[] chars = longURL.toCharArray();

		for(int i= chars.length-1; i>= 0; i--) {
			n+=ALPHABET.indexOf(chars[i]) * (int) Math.pow(62, i);
		}
		return fromBase10(n);

	}

	private static String fromBase10(long i) {
		StringBuilder sb = new StringBuilder("");
		if (i == 0) {
			return "a";
		}
		while (i > 0) {
			i = fromBase10(i, sb);
		}
		return sb.reverse().toString();
	}

	private static long fromBase10(long i, final StringBuilder sb) {
		long rem = i % BASE;
		sb.append(ALPHABET.charAt((int)rem));
		return i / BASE;
	}

	private static long toBase10(String str) {
		return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
	}

	private static long toBase10(char[] chars) {
		long n = 0;
		for (int i = chars.length - 1; i >= 0; i--) {
			n += toBase10(ALPHABET.indexOf(chars[i]), i);
		}
		return n;
	}

	private static int toBase10(int n, int pow) {
		return n * (int) Math.pow(BASE, pow);
	}
}
