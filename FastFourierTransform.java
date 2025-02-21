/*
Fast Fourier Transform

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class FastFourierTransform {
	private Complex[] tf;
	private Complex tmp0, tmp1, tmp2, tmp3;

	FastFourierTransform(int length) {
		if (length < 2 || !isPowerOfTwo(length))
			throw new IllegalArgumentException("Transform length must be a power of 2 and at least 2, but was: " + length);
		tf = new Complex[length];
		for (int i = 0; i < length; ++i) {
			double x = - (2.0 * Math.PI * i) / length;
			float a = (float)Math.cos(x);
			float b = (float)Math.sin(x);
			tf[i] = new Complex(a, b);
		}
		tmp0 = new Complex();
		tmp1 = new Complex();
		tmp2 = new Complex();
		tmp3 = new Complex();
	}

	private boolean isPowerOfTwo(int n) {
		return n > 0 && (n & (n - 1)) == 0;
	}

	private void dft2(Complex out0, Complex out1, Complex in0, Complex in1) {
		out0.set(in0).add(in1);
		out1.set(in0).sub(in1);
	}

	private void dft4(Complex out0, Complex out1, Complex out2, Complex out3, Complex in0, Complex in1, Complex in2, Complex in3) {
		tmp0.set(in0).add(in2);
		tmp1.set(in0).sub(in2);
		tmp2.set(in1).add(in3);
		tmp3.set(in1.imag - in3.imag, in3.real - in1.real);
		out0.set(tmp0).add(tmp2);
		out1.set(tmp1).add(tmp3);
		out2.set(tmp0).sub(tmp2);
		out3.set(tmp1).sub(tmp3);
	}

	private void radix2(Complex[] out, Complex[] in, int O, int I, int N, int S, boolean F) {
		if (N == 2) {
			dft2(out[O], out[O + 1], in[I], in[I + S]);
		} else if (N == 4) {
			if (F)
				dft4(out[O], out[O + 1], out[O + 2], out[O + 3], in[I], in[I + S], in[I + 2 * S], in[I + 3 * S]);
			else
				dft4(out[O], out[O + 3], out[O + 2], out[O + 1], in[I], in[I + S], in[I + 2 * S], in[I + 3 * S]);
		} else {
			radix2(out, in, O, I, N / 2, 2 * S, F);
			radix2(out, in, O + N / 2, I + S, N / 2, 2 * S, F);
			for (int k0 = 0, k1 = N / 2; k0 < N / 2; ++k0, ++k1) {
				tmp0.set(out[O + k0]);
				tmp1.set(tf[k0 * S]);
				if (!F)
					tmp1.conj();
				tmp1.mul(out[O + k1]);
				dft2(out[O + k0], out[O + k1], tmp0, tmp1);
			}
		}
	}

	void forward(Complex[] out, Complex[] in) {
		if (in.length != tf.length)
			throw new IllegalArgumentException("Input array length (" + in.length + ") must be equal to Transform length (" + tf.length + ")");
		if (out.length != tf.length)
			throw new IllegalArgumentException("Output array length (" + out.length + ") must be equal to Transform length (" + tf.length + ")");
		radix2(out, in, 0, 0, tf.length, 1, true);
	}

	void backward(Complex[] out, Complex[] in) {
		if (in.length != tf.length)
			throw new IllegalArgumentException("Input array length (" + in.length + ") must be equal to Transform length (" + tf.length + ")");
		if (out.length != tf.length)
			throw new IllegalArgumentException("Output array length (" + out.length + ") must be equal to Transform length (" + tf.length + ")");
		radix2(out, in, 0, 0, tf.length, 1, false);
	}
}
