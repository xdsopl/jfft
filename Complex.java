/*
Complex math

Copyright 2024 Ahmet Inan <xdsopl@gmail.com>
*/

public class Complex {
	public double real, imag;

	Complex() {
		real = 0;
		imag = 0;
	}

	Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	Complex(Complex other) {
		this.real = other.real;
		this.imag = other.imag;
	}

	Complex set(Complex other) {
		real = other.real;
		imag = other.imag;
		return this;
	}

	Complex set(double real, double imag) {
		this.real = real;
		this.imag = imag;
		return this;
	}

	Complex set(double real) {
		return set(real, 0);
	}

	double norm() {
		return real * real + imag * imag;
	}

	double abs() {
		return Math.sqrt(norm());
	}

	double arg() {
		return Math.atan2(imag, real);
	}

	Complex polar(double a, double b) {
		real = a * Math.cos(b);
		imag = a * Math.sin(b);
		return this;
	}

	Complex conj() {
		imag = -imag;
		return this;
	}

	Complex add(Complex other) {
		real += other.real;
		imag += other.imag;
		return this;
	}

	Complex sub(Complex other) {
		real -= other.real;
		imag -= other.imag;
		return this;
	}

	Complex mul(double value) {
		real *= value;
		imag *= value;
		return this;
	}

	Complex mul(Complex other) {
		double tmp = real * other.real - imag * other.imag;
		imag = real * other.imag + imag * other.real;
		real = tmp;
		return this;
	}

	Complex div(double value) {
		real /= value;
		imag /= value;
		return this;
	}

	Complex div(Complex other) {
		double den = other.norm();
		double tmp = (real * other.real + imag * other.imag) / den;
		imag = (imag * other.real - real * other.imag) / den;
		real = tmp;
		return this;
	}

	@Override
	public String toString() {
		return "(" + real + " + " + imag + "i)";
	}
}
