/*
Short Time Fourier Transform

Copyright 2025 Ahmet Inan <xdsopl@gmail.com>
*/

public class ShortTimeFourierTransform {
        private FastFourierTransform fft;
        private Complex[] prev, freq;
        private double factor;
        private int index;

        public double[] power;

        ShortTimeFourierTransform(int length) {
                fft = new FastFourierTransform(length);
                prev = new Complex[length];
                for (int i = 0; i < length; ++i)
                        prev[i] = new Complex();
                freq = new Complex[length];
                for (int i = 0; i < length; ++i)
                        freq[i] = new Complex();
                power = new double[length];
                factor = 1.0 / length;
        }

        boolean push(Complex input) {
                prev[index].set(input).mul(factor);
                if (++index >= prev.length)
                        index = 0;
                else
                        return false;
                fft.forward(freq, prev);
                for (int i = 0; i < power.length; ++i)
                        power[i] = freq[i].norm();
                return true;
        }
}