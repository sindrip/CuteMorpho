CuteMorpho.class: CuteMorpho.java
	javac CuteMorpho.java
CuteMorpho.java: cutemorpho.jflex
	java -jar jflex-1.6.1.jar cutemorpho.jflex
clean:
	rm -Rf *~ CuteMorpho*.class CuteMorpho.java
test: CuteMorpho.class
	java CuteMorpho test.cmorpho
build: CuteMorpho.class