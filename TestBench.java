/*
Test bench

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class TestBench {
	public static void main(String[] args) {
		Complex z1 = new Complex(12.75f, -4.5f);
		Complex z2 = new Complex(3.0f, -1.5f);

		Complex sum = new Complex(z1);
		sum.add(z2);

		Complex product = new Complex(z1);
		product.mul(z2);

		System.out.println("z1 = " + z1);
		System.out.println("z2 = " + z2);
		System.out.println("(z1 + z2) = " + sum);
		System.out.println("(z1 * z2) = " + product);
	}
}
