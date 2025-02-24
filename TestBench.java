/*
Test bench

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

import java.util.Random;

public class TestBench {
	public static void main(String[] args) {
		int length = 360;
		FastFourierTransform fft = new FastFourierTransform(length);
		Random random = new Random();
		Complex[] buf0 = new Complex[length];
		Complex[] buf1 = new Complex[length];
		Complex[] buf2 = new Complex[length];
		for (int i = 0; i < length; ++i) {
			double a = random.nextDouble() * 2 - 1;
			double b = random.nextDouble() * 2 - 1;
			buf0[i] = new Complex(a, b);
		}
		for (int i = 0; i < length; ++i)
			buf1[i] = new Complex();
		for (int i = 0; i < length; ++i)
			buf2[i] = new Complex();
		fft.forward(buf1, buf0);
		fft.backward(buf2, buf1);
		double factor = 1.0 / length;
		for (int i = 0; i < length; ++i)
			buf2[i].mul(factor);
		double maxError = 0;
		for (int i = 0; i < length; ++i)
			maxError = Math.max(maxError, buf2[i].sub(buf0[i]).abs());
		System.out.println("max error = " + maxError);
		int iterations = 100000;
		for (int j = 0; j < iterations; ++j) {
			fft.backward(buf2, buf1);
			for (int i = 0; i < length; ++i)
				buf2[i].mul(factor);
			fft.forward(buf1, buf2);
		}
		long before = System.nanoTime();
		for (int j = 0; j < iterations; ++j) {
			fft.backward(buf2, buf1);
			for (int i = 0; i < length; ++i)
				buf2[i].mul(factor);
			fft.forward(buf1, buf2);
		}
		long after = System.nanoTime();
		fft.backward(buf2, buf1);
		for (int i = 0; i < length; ++i)
			buf2[i].mul(factor);
		for (int i = 0; i < length; ++i)
			maxError = Math.max(maxError, buf2[i].sub(buf0[i]).abs());
		System.out.println("max error after " + 2 * iterations + " iterations = " + maxError);
		long duration = after - before;
		System.out.println("duration per iteration = " + duration / iterations + " ns");
	}
}
