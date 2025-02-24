/*
Fast Fourier Transform

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class FastFourierTransform {
	private final Complex[] tf;
	private final Complex tmp0, tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7, tmp8;
	private final Complex tin0, tin1, tin2, tin3, tin4;

	FastFourierTransform(int length) {
		int rest = length;
		while (rest > 1) {
			if (rest % 2 == 0)
				rest /= 2;
			else if (rest % 3 == 0)
				rest /= 3;
			else if (rest % 5 == 0)
				rest /= 5;
			else
				break;
		}
		if (length < 2 || rest != 1)
			throw new IllegalArgumentException(
				"Transform length must be a composite of 2, 3 and 5 and at least 2, but was: "
				+ length);
		tf = new Complex[length];
		for (int i = 0; i < length; ++i) {
			double x = -(2.0 * Math.PI * i) / length;
			double a = Math.cos(x);
			double b = Math.sin(x);
			tf[i] = new Complex(a, b);
		}
		tmp0 = new Complex();
		tmp1 = new Complex();
		tmp2 = new Complex();
		tmp3 = new Complex();
		tmp4 = new Complex();
		tmp5 = new Complex();
		tmp6 = new Complex();
		tmp7 = new Complex();
		tmp8 = new Complex();
		tin0 = new Complex();
		tin1 = new Complex();
		tin2 = new Complex();
		tin3 = new Complex();
		tin4 = new Complex();
	}

	private boolean isPowerOfTwo(int n) {
		return n > 0 && (n & (n - 1)) == 0;
	}

	private boolean isPowerOfFour(int n) {
		return isPowerOfTwo(n) && (n & 0x55555555) != 0;
	}

	private double cos(int n, int N) {
		return Math.cos(n * 2.0 * Math.PI / N);
	}

	private double sin(int n, int N) {
		return Math.sin(n * 2.0 * Math.PI / N);
	}

	private void dft2(Complex out0, Complex out1, Complex in0, Complex in1) {
		out0.set(in0).add(in1);
		out1.set(in0).sub(in1);
	}

	private void radix2(Complex[] out, Complex[] in, int O, int I, int N, int S, boolean F) {
		int Q = N / 2;
		dit(out, in, O, I, Q, 2 * S, F);
		dit(out, in, O + Q, I + S, Q, 2 * S, F);
		for (int k0 = O, k1 = O + Q, l1 = 0; k0 < O + Q; ++k0, ++k1, l1 += S) {
			tin0.set(out[k0]);
			tin1.set(tf[l1]);
			if (!F)
				tin1.conj();
			tin1.mul(out[k1]);
			dft2(out[k0], out[k1], tin0, tin1);
		}
	}

	private void fwd3(Complex out0, Complex out1, Complex out2, Complex in0, Complex in1, Complex in2) {
		tmp0.set(in1).add(in2);
		tmp1.set(in1.imag - in2.imag, in2.real - in1.real);
		tmp2.set(tmp0).mul(-0.5);
		tmp3.set(tmp1).mul(0.5 * Math.sqrt(3.0));
		out0.set(in0).add(tmp0);
		out1.set(in0).add(tmp2).add(tmp3);
		out2.set(in0).add(tmp2).sub(tmp3);
	}

	private void radix3(Complex[] out, Complex[] in, int O, int I, int N, int S, boolean F) {
		int Q = N / 3;
		dit(out, in, O, I, Q, 3 * S, F);
		dit(out, in, O + Q, I + S, Q, 3 * S, F);
		dit(out, in, O + 2 * Q, I + 2 * S, Q, 3 * S, F);
		for (int k0 = O, k1 = O + Q, k2 = O + 2 * Q, l1 = 0, l2 = 0;
				k0 < O + Q; ++k0, ++k1, ++k2, l1 += S, l2 += 2 * S) {
			tin0.set(out[k0]);
			tin1.set(tf[l1]);
			if (!F)
				tin1.conj();
			tin1.mul(out[k1]);
			tin2.set(tf[l2]);
			if (!F)
				tin2.conj();
			tin2.mul(out[k2]);
			if (F)
				fwd3(out[k0], out[k1], out[k2], tin0, tin1, tin2);
			else
				fwd3(out[k0], out[k2], out[k1], tin0, tin1, tin2);
		}
	}

	private void fwd4(Complex out0, Complex out1, Complex out2, Complex out3,
			Complex in0, Complex in1, Complex in2, Complex in3) {
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
		int Q = N / 4;
		dit(out, in, O, I, Q, 4 * S, F);
		dit(out, in, O + Q, I + S, Q, 4 * S, F);
		dit(out, in, O + 2 * Q, I + 2 * S, Q, 4 * S, F);
		dit(out, in, O + 3 * Q, I + 3 * S, Q, 4 * S, F);
		for (int k0 = O, k1 = O + Q, k2 = O + 2 * Q, k3 = O + 3 * Q, l1 = 0, l2 = 0, l3 = 0;
				k0 < O + Q; ++k0, ++k1, ++k2, ++k3, l1 += S, l2 += 2 * S, l3 += 3 * S) {
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

	private void fwd5(Complex out0, Complex out1, Complex out2, Complex out3, Complex out4,
			Complex in0, Complex in1, Complex in2, Complex in3, Complex in4) {
		tmp0.set(in1).add(in4);
		tmp1.set(in2).add(in3);
		tmp2.set(in1.imag - in4.imag, in4.real - in1.real);
		tmp3.set(in2.imag - in3.imag, in3.real - in2.real);
		tmp5.set(tmp0).mul(cos(1, 5)).add(tmp4.set(tmp1).mul(cos(2, 5)));
		tmp6.set(tmp2).mul(sin(1, 5)).add(tmp4.set(tmp3).mul(sin(2, 5)));
		tmp7.set(tmp0).mul(cos(2, 5)).add(tmp4.set(tmp1).mul(cos(1, 5)));
		tmp8.set(tmp2).mul(sin(2, 5)).sub(tmp4.set(tmp3).mul(sin(1, 5)));
		out0.set(in0).add(tmp0).add(tmp1);
		out1.set(in0).add(tmp5).add(tmp6);
		out2.set(in0).add(tmp7).add(tmp8);
		out3.set(in0).add(tmp7).sub(tmp8);
		out4.set(in0).add(tmp5).sub(tmp6);
	}

	private void radix5(Complex[] out, Complex[] in, int O, int I, int N, int S, boolean F) {
		int Q = N / 5;
		dit(out, in, O, I, Q, 5 * S, F);
		dit(out, in, O + Q, I + S, Q, 5 * S, F);
		dit(out, in, O + 2 * Q, I + 2 * S, Q, 5 * S, F);
		dit(out, in, O + 3 * Q, I + 3 * S, Q, 5 * S, F);
		dit(out, in, O + 4 * Q, I + 4 * S, Q, 5 * S, F);
		for (int k0 = O, k1 = O + Q, k2 = O + 2 * Q, k3 = O + 3 * Q, k4 = O + 4 * Q, l1 = 0, l2 = 0, l3 = 0, l4 = 0;
			k0 < O + Q; ++k0, ++k1, ++k2, ++k3, ++k4, l1 += S, l2 += 2 * S, l3 += 3 * S, l4 += 4 * S) {
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
			tin4.set(tf[l4]);
			if (!F)
				tin4.conj();
			tin4.mul(out[k4]);
			if (F)
				fwd5(out[k0], out[k1], out[k2], out[k3], out[k4], tin0, tin1, tin2, tin3, tin4);
			else
				fwd5(out[k0], out[k4], out[k3], out[k2], out[k1], tin0, tin1, tin2, tin3, tin4);
		}
	}

	private void dit(Complex[] out, Complex[] in, int O, int I, int N, int S, boolean F) {
		if (N == 2) {
			dft2(out[O], out[O + 1], in[I], in[I + S]);
		} else if (N == 3) {
			if (F)
				fwd3(out[O], out[O + 1], out[O + 2],
					in[I], in[I + S], in[I + 2 * S]);
			else
				fwd3(out[O], out[O + 2], out[O + 1],
					in[I], in[I + S], in[I + 2 * S]);
		} else if (N == 4) {
			if (F)
				fwd4(out[O], out[O + 1], out[O + 2], out[O + 3],
					in[I], in[I + S], in[I + 2 * S], in[I + 3 * S]);
			else
				fwd4(out[O], out[O + 3], out[O + 2], out[O + 1],
					in[I], in[I + S], in[I + 2 * S], in[I + 3 * S]);
		} else if (N == 5) {
			if (F)
				fwd5(out[O], out[O + 1], out[O + 2], out[O + 3], out[O + 4],
					in[I], in[I + S], in[I + 2 * S], in[I + 3 * S], in[I + 4 * S]);
			else
				fwd5(out[O], out[O + 4], out[O + 3], out[O + 2], out[O + 1],
					in[I], in[I + S], in[I + 2 * S], in[I + 3 * S], in[I + 4 * S]);
		} else if (N % 5 == 0) {
			radix5(out, in, O, I, N, S, F);
		} else if (N % 3 == 0) {
			radix3(out, in, O, I, N, S, F);
		} else if (isPowerOfFour(N)) {
			radix4(out, in, O, I, N, S, F);
		} else {
			radix2(out, in, O, I, N, S, F);
		}
	}

	void forward(Complex[] out, Complex[] in) {
		if (in.length != tf.length)
			throw new IllegalArgumentException("Input array length (" + in.length
				+ ") must be equal to Transform length (" + tf.length + ")");
		if (out.length != tf.length)
			throw new IllegalArgumentException("Output array length (" + out.length
				+ ") must be equal to Transform length (" + tf.length + ")");
		dit(out, in, 0, 0, tf.length, 1, true);
	}

	void backward(Complex[] out, Complex[] in) {
		if (in.length != tf.length)
			throw new IllegalArgumentException("Input array length (" + in.length
				+ ") must be equal to Transform length (" + tf.length + ")");
		if (out.length != tf.length)
			throw new IllegalArgumentException("Output array length (" + out.length
				+ ") must be equal to Transform length (" + tf.length + ")");
		dit(out, in, 0, 0, tf.length, 1, false);
	}
}
