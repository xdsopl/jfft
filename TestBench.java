/*
Test bench

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

import java.util.Random;

public class TestBench {
	public static void main(String[] args) {
		int length = 8;
		FastFourierTransform fft = new FastFourierTransform(length);
		Random random = new Random();
		Complex[] buf0 = new Complex[length];
		Complex[] buf1 = new Complex[length];
		Complex[] buf2 = new Complex[length];
		for (int i = 0; i < length; ++i) {
			float a = random.nextFloat() * 2 - 1;
			float b = random.nextFloat() * 2 - 1;
			buf0[i] = new Complex(a, b);
		}
		for (int i = 0; i < length; ++i)
			buf1[i] = new Complex();
		for (int i = 0; i < length; ++i)
			buf2[i] = new Complex();
		System.out.println("Testing forward transform");
		fft.forward(buf1, buf0);
		for (int i = 0; i < length; ++i)
			System.out.println("buf0[" + i + "] = " + buf0[i]);
		for (int i = 0; i < length; ++i)
			System.out.println("buf1[" + i + "] = " + buf1[i]);
		System.out.println("Testing backward transform");
		fft.backward(buf2, buf1);
		for (int i = 0; i < length; ++i)
			System.out.println("buf1[" + i + "] = " + buf1[i]);
		for (int i = 0; i < length; ++i)
			buf2[i].div(length);
		for (int i = 0; i < length; ++i)
			System.out.println("buf2[" + i + "] = " + buf2[i]);
		for (int i = 0; i < length; ++i)
			System.out.println("abs(buf2[" + i + "] - buf0[" + i + "]) = " + buf2[i].sub(buf0[i]).abs());
	}
}
