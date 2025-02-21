/*
Fast Fourier Transform

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class FastFourierTransform {
	private Complex[] tf;
	private Complex tmp0, tmp1, tmp2, tmp3;
	private Complex tin0, tin1, tin2, tin3;

	FastFourierTransform(int length) {
		if (length < 2 || !isPowerOfTwo(length))
			throw new IllegalArgumentException("Transform length must be a power of 2 and at least 2, but was: " + length);
		tf = new Complex[length];
		for (int i = 0; i < length; ++i) {
			double x = - (2.0 * Math.PI * i) / length;
			double a = Math.cos(x);
			double b = Math.sin(x);
			tf[i] = new Complex(a, b);
		}
		tmp0 = new Complex();
		tmp1 = new Complex();
		tmp2 = new Complex();
		tmp3 = new Complex();
		tin0 = new Complex();
		tin1 = new Complex();
		tin2 = new Complex();
		tin3 = new Complex();
	}

	private boolean isPowerOfTwo(int n) {
		return n > 0 && (n & (n - 1)) == 0;
	}

	private boolean isPowerOfFour(int n) {
		return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
	}

	private void fwd2(Complex out0, Complex out1, Complex in0, Complex in1) {
		out0.set(in0).add(in1);
		out1.set(in0).sub(in1);
	}

	private void fwd4(Complex out0, Complex out1, Complex out2, Complex out3, Complex in0, Complex in1, Complex in2, Complex in3) {
		tmp0.set(in0).add(in2);
		tmp1.set(in0).sub(in2);
		tmp2.set(in1).add(in3);
		tmp3.set(in1.imag - in3.imag, in3.real - in1.real);
		out0.set(tmp0).add(tmp2);
		out1.set(tmp1).add(tmp3);
		out2.set(tmp0).sub(tmp2);
		out3.set(tmp1).sub(tmp3);
	}

	private void radix4(Complex[] out, Complex[] in, int O, int I, int N, int S, boolean F) {
		if (N == 4) {
			if (F)
				fwd4(out[O], out[O + 1], out[O + 2], out[O + 3], in[I], in[I + S], in[I + 2 * S], in[I + 3 * S]);
			else
				fwd4(out[O], out[O + 3], out[O + 2], out[O + 1], in[I], in[I + S], in[I + 2 * S], in[I + 3 * S]);
		} else {
			int Q = N / 4;
			radix4(out, in, O, I, Q, 4 * S, F);
			radix4(out, in, O + Q, I + S, Q, 4 * S, F);
			radix4(out, in, O + 2 * Q , I + 2 * S, Q, 4 * S, F);
			radix4(out, in, O + 3 * Q, I + 3 * S, Q, 4 * S, F);
			for (int k0 = O, k1 = O + Q, k2 = O + 2 * Q, k3 = O + 3 * Q, l1 = 0, l2 = 0, l3 = 0; k0 < O + Q; ++k0, ++k1, ++k2, ++k3, l1 += S, l2 += 2 * S, l3 += 3 * S) {
				tin0.set(out[k0]);
				tin1.set(tf[l1]);
				if (!F)
					tin1.conj();
				tin1.mul(out[k1]);
				tin2.set(tf[l2]);
				if (!F)
					tin2.conj();
				tin2.mul(out[k2]);
				tin3.set(tf[l3]);
				if (!F)
					tin3.conj();
				tin3.mul(out[k3]);
				if (F)
					fwd4(out[k0], out[k1], out[k2], out[k3], tin0, tin1, tin2, tin3);
				else
					fwd4(out[k0], out[k3], out[k2], out[k1], tin0, tin1, tin2, tin3);
			}
		}
	}

	private void radix2(Complex[] out, Complex[] in, int O, int I, int N, int S, boolean F) {
		if (N == 2) {
			fwd2(out[O], out[O + 1], in[I], in[I + S]);
		} else if (isPowerOfFour(N)) {
			radix4(out, in, O, I, N, S, F);
		} else {
			int Q = N / 2;
			radix2(out, in, O, I, Q, 2 * S, F);
			radix2(out, in, O + Q, I + S, Q, 2 * S, F);
			for (int k0 = O, k1 = O + Q, l1 = 0; k0 < O + Q; ++k0, ++k1, l1 += S) {
				tin0.set(out[k0]);
				tin1.set(tf[l1]);
				if (!F)
					tin1.conj();
				tin1.mul(out[k1]);
				fwd2(out[k0], out[k1], tin0, tin1);
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
