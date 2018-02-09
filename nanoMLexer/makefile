NanoMLexer.class: NanoMLexer.java
	javac NanoMLexer.java
NanoMLexer.java: nanomlexer.jflex
	java -jar jflex-1.6.1.jar nanomlexer.jflex
clean:
	rm -Rf *~ NanoMLexer*.class NanoMLexer.java
test: NanoMLexer.class test2.s
	java NanoMLexer test2.s