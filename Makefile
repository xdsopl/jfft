JAVAC = javac
JAVA  = java
GNUPLOT = gnuplot

test: TestBench.class
	$(JAVA) TestBench

sweep: FreqSweep.class
	$(GNUPLOT) sweep.gp

TestBench.class: TestBench.java Complex.java FastFourierTransform.java
	$(JAVAC) TestBench.java

FreqSweep.class: FreqSweep.java Complex.java FastFourierTransform.java ShortTimeFourierTransform.java Hann.java
	$(JAVAC) FreqSweep.java

clean:
	rm -f TestBench.class Complex.class FastFourierTransform.class ShortTimeFourierTransform.class FreqSweep.class Hann.class
