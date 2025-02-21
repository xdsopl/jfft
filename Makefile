JAVAC = javac
JAVA  = java

JAVA_FILES = TestBench.java Complex.java FastFourierTransform.java
CLASS_FILES = TestBench.class Complex.class FastFourierTransform.class

test: TestBench.class
	$(JAVA) TestBench

TestBench.class: $(JAVA_FILES)
	$(JAVAC) TestBench.java

clean:
	rm -f $(CLASS_FILES)
