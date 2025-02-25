JAVAC = javac
JAVA  = java
GNUPLOT = gnuplot

test: TestBench.class
	$(JAVA) TestBench 1
	$(JAVA) TestBench 2
	$(JAVA) TestBench 3
	$(JAVA) TestBench 4
	$(JAVA) TestBench 5
	$(JAVA) TestBench 6
	$(JAVA) TestBench 7
	$(JAVA) TestBench 8
	$(JAVA) TestBench 9
	$(JAVA) TestBench 10
	$(JAVA) TestBench 12
	$(JAVA) TestBench 14
	$(JAVA) TestBench 15
	$(JAVA) TestBench 16
	$(JAVA) TestBench 18
	$(JAVA) TestBench 20
	$(JAVA) TestBench 21
	$(JAVA) TestBench 24
	$(JAVA) TestBench 25
	$(JAVA) TestBench 27
	$(JAVA) TestBench 28
	$(JAVA) TestBench 30
	$(JAVA) TestBench 32
	$(JAVA) TestBench 35
	$(JAVA) TestBench 36
	$(JAVA) TestBench 40
	$(JAVA) TestBench 42
	$(JAVA) TestBench 64
	$(JAVA) TestBench 90
	$(JAVA) TestBench 120
	$(JAVA) TestBench 128
	$(JAVA) TestBench 160
	$(JAVA) TestBench 180
	$(JAVA) TestBench 240
	$(JAVA) TestBench 256
	$(JAVA) TestBench 320
	$(JAVA) TestBench 360
	$(JAVA) TestBench 480
	$(JAVA) TestBench 512
	$(JAVA) TestBench 600
	$(JAVA) TestBench 640
	$(JAVA) TestBench 720
	$(JAVA) TestBench 800
	$(JAVA) TestBench 1024
	$(JAVA) TestBench 1080
	$(JAVA) TestBench 1280
	$(JAVA) TestBench 1920
	$(JAVA) TestBench 2048
	$(JAVA) TestBench 4096
	$(JAVA) TestBench 8000
	$(JAVA) TestBench 8192
	$(JAVA) TestBench 11025
	$(JAVA) TestBench 16000
	$(JAVA) TestBench 16384
	$(JAVA) TestBench 22050
	$(JAVA) TestBench 32000
	$(JAVA) TestBench 32768
	$(JAVA) TestBench 44100
	$(JAVA) TestBench 48000
	$(JAVA) TestBench 65536

sweep: FreqSweep.class
	$(GNUPLOT) sweep.gp

TestBench.class: TestBench.java Complex.java FastFourierTransform.java
	$(JAVAC) TestBench.java

FreqSweep.class: FreqSweep.java Complex.java FastFourierTransform.java ShortTimeFourierTransform.java Hann.java
	$(JAVAC) FreqSweep.java

clean:
	rm -f TestBench.class Complex.class FastFourierTransform.class ShortTimeFourierTransform.class FreqSweep.class Hann.class
