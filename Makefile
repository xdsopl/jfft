JAVAC = javac
JAVA  = java
GNUPLOT = gnuplot

JAVA_FILES = TestBench.java Complex.java FastFourierTransform.java ShortTimeFourierTransform.java FreqSweep.java Hann.java
CLASS_FILES = TestBench.class Complex.class FastFourierTransform.class ShortTimeFourierTransform.class FreqSweep.class Hann.class

test: TestBench.class
	$(JAVA) TestBench

sweep: FreqSweep.class
	$(GNUPLOT) FreqSweep.gp

TestBench.class: $(JAVA_FILES)
	$(JAVAC) TestBench.java

FreqSweep.class: $(JAVA_FILES)
	$(JAVAC) FreqSweep.java

clean:
	rm -f $(CLASS_FILES)
