/*
Frequency sweep test of STFT

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class FreqSweep {
	public static void main(String[] args) {
		int length = 420;
		ShortTimeFourierTransform stft = new ShortTimeFourierTransform(length, 3);
		Complex input = new Complex();
		int factor = 10000, range = length * factor;
		for (int acc = 0, freq = -10 * factor; freq <= 10 * factor; acc = (acc + ++freq) % range)
			if (stft.push(input.polar(1, (acc * 2 * Math.PI) / range)))
				System.out.println(freq / (double) factor + " "
					+ dB(stft.power[length - 1]) + " " + dB(stft.power[0]) + " " + dB(stft.power[1]));
	}

	private static double dB(double power) {
		return 10.0 * Math.log10(power);
	}
}
