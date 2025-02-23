/*
Frequency sweep test of STFT

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class FreqSweep {
	public static void main(String[] args) {
		int length = 256;
		ShortTimeFourierTransform stft = new ShortTimeFourierTransform(length);
		Complex input = new Complex();
		int factor = 1000, range = length * factor;
		for (int acc = 0, freq = -range / 2; freq <= range / 2; acc = (acc + ++freq) % range)
			if (stft.push(input.polar(1, (acc * 2 * Math.PI) / range)))
				System.out.println(freq / (double) factor + " "
					+ stft.power[length - 1] + " " + stft.power[0] + " " + stft.power[1]);
	}
}
