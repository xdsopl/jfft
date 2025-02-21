/*
Fast Fourier Transform

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class FFT {
	public Complex[] input;
	public Complex[] output;
	public Complex[] factor;

	private Complex tmp0, tmp1;

	FFT(int length, int sign) {
		input = new Complex[length];
		for (int i = 0; i < length; ++i)
			input[i] = new Complex();
		output = new Complex[length];
		for (int i = 0; i < length; ++i)
			output[i] = new Complex();
		factor = new Complex[length];
		for (int i = 0; i < length; ++i) {
			double x = (sign * 2.0 * Math.PI * i) / length;
			float a = (float)Math.cos(x);
			float b = (float)Math.sin(x);
			factor[i] = new Complex(a, b);
		}
		tmp0 = new Complex();
		tmp1 = new Complex();
	}

	void dft2(Complex out0, Complex out1, Complex in0, Complex in1) {
		tmp0.set(in0);
		tmp1.set(in1);
		out0.set(tmp0).add(tmp1);
		out1.set(tmp0).sub(tmp1);
	}

	void radix2(int O, int I, int N, int S) {
		switch (N) {
			case 1:
				output[O].set(input[I]);
				return;
			case 2:
				dft2(output[O], output[O + 1], input[I], input[I + S]);
				return;
		}
		radix2(O, I, N / 2, 2 * S);
		radix2(O + N / 2, I + S, N / 2, 2 * S);
		for (int k0 = 0, k1 = N / 2; k0 < N / 2; ++k0, ++k1)
			dft2(output[O + k0], output[O + k1], output[O + k0], output[O + k1].mul(factor[k0 * S]));
	}

	void trans() {
		radix2(0, 0, input.length, 1);
	}
}
