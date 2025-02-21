/*
Test bench

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class TestBench {
	public static void main(String[] args) {
		int length = 8;
		FFT fft = new FFT(length);
		Complex[] buf0 = new Complex[length];
		Complex[] buf1 = new Complex[length];
		for (int i = 0; i < length; ++i)
			buf0[i] = new Complex();
		for (int i = 0; i < length; ++i)
			buf1[i] = new Complex();
		System.out.println("Testing forward transform");
		for (int i = 0; i < buf0.length; ++i)
			buf0[i].set(1, 0);
		fft.forward(buf1, buf0);
		for (int i = 0; i < length; ++i)
			System.out.println("buf0[" + i + "] = " + buf0[i]);
		for (int i = 0; i < length; ++i)
			System.out.println("buf1[" + i + "] = " + buf1[i]);
		System.out.println("Testing backward transform");
		buf1[0].set(1, 0);
		fft.backward(buf0, buf1);
		for (int i = 0; i < length; ++i)
			System.out.println("buf1[" + i + "] = " + buf1[i]);
		for (int i = 0; i < length; ++i)
			System.out.println("buf0[" + i + "] = " + buf0[i]);
	}
}
