JAVAC = javac
JAVA  = java

JAVA_FILES = TestBench.java Complex.java FFT.java
CLASS_FILES = TestBench.class Complex.class FFT.class

test: TestBench.class
	$(JAVA) TestBench

TestBench.class: $(JAVA_FILES)
	$(JAVAC) TestBench.java

clean:
	rm -f $(CLASS_FILES)
