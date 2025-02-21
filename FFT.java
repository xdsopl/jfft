/*
Fast Fourier Transform

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class FFT {
	private Complex[] tf;
	private Complex tmp0, tmp1;

	FFT(int length) {
		if (length < 2 || !isPowerOfTwo(length))
			throw new IllegalArgumentException("FFT length must be a power of 2 and at least 2, but was: " + length);
		tf = new Complex[length];
		for (int i = 0; i < length; ++i) {
			double x = - (2.0 * Math.PI * i) / length;
			float a = (float)Math.cos(x);
			float b = (float)Math.sin(x);
			tf[i] = new Complex(a, b);
		}
		tmp0 = new Complex();
		tmp1 = new Complex();
	}

	private boolean isPowerOfTwo(int n) {
		return n > 0 && (n & (n - 1)) == 0;
	}

	private void dft2(Complex out0, Complex out1, Complex in0, Complex in1) {
		tmp0.set(in0);
		tmp1.set(in1);
		out0.set(tmp0).add(tmp1);
		out1.set(tmp0).sub(tmp1);
	}

	private void radix2(Complex[] out, Complex[] in, int O, int I, int N, int S, boolean D) {
		if (N == 2) {
			dft2(out[O], out[O + 1], in[I], in[I + S]);
		} else {
			radix2(out, in, O, I, N / 2, 2 * S, D);
			radix2(out, in, O + N / 2, I + S, N / 2, 2 * S, D);
			for (int k0 = 0, k1 = N / 2; k0 < N / 2; ++k0, ++k1)
				dft2(out[O + k0], out[O + k1], out[O + k0], out[O + k1].mul(D ? tf[k0 * S] : tmp1.set(tf[k0 * S]).conj()));
		}
	}

	void forward(Complex[] out, Complex[] in) {
		radix2(out, in, 0, 0, tf.length, 1, true);
	}

	void backward(Complex[] out, Complex[] in) {
		radix2(out, in, 0, 0, tf.length, 1, false);
	}
}
