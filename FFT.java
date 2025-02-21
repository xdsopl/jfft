/*
Fast Fourier Transform

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class FFT {
	public Complex[] factor;

	private Complex tmp0, tmp1;

	FFT(int length, int sign) {
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

	void radix2(Complex[] out, Complex[] in, int O, int I, int N, int S) {
		switch (N) {
			case 1:
				out[O].set(in[I]);
				return;
			case 2:
				dft2(out[O], out[O + 1], in[I], in[I + S]);
				return;
		}
		radix2(out, in, O, I, N / 2, 2 * S);
		radix2(out, in, O + N / 2, I + S, N / 2, 2 * S);
		for (int k0 = 0, k1 = N / 2; k0 < N / 2; ++k0, ++k1)
			dft2(out[O + k0], out[O + k1], out[O + k0], out[O + k1].mul(factor[k0 * S]));
	}

	void trans(Complex[] out, Complex[] in) {
		radix2(out, in, 0, 0, factor.length, 1);
	}
}
