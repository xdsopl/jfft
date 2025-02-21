/*
Test bench

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class TestBench {
	public static void main(String[] args) {
		System.out.println("Testing forward transform");
		FFT fwd = new FFT(8, -1);
		for (int i = 0; i < fwd.input.length; ++i)
			fwd.input[i].set(1, 0);
		fwd.trans();
		for (int i = 0; i < fwd.input.length; ++i)
			System.out.println("input[" + i + "] = " + fwd.input[i]);
		for (int i = 0; i < fwd.input.length; ++i)
			System.out.println("output[" + i + "] = " + fwd.output[i]);
		System.out.println("Testing backward transform");
		FFT bwd = new FFT(8, 1);
		bwd.input[0].set(1, 0);
		bwd.trans();
		for (int i = 0; i < bwd.input.length; ++i)
			System.out.println("input[" + i + "] = " + bwd.input[i]);
		for (int i = 0; i < bwd.input.length; ++i)
			System.out.println("output[" + i + "] = " + bwd.output[i]);
	}
}
