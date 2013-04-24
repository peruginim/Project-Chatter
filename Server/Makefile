#The Makefile for the Server
#Project Chatter 2013

#Java Compliler and Flags
JFLAGS = -g
JC = javac
JLIB = Chatter.jar 
#RXTXcomm.jar


CLASSES = \
	CustomProtocol.java

#Create .class files from .java files
all: $(CLASSES)
	$(JC) $(JFLAGS) *.java -cp $(JLIB)

keygen: ChatterRSA.class
	java ChatterRSA

run: CustomProtocol.class
	java -cp .:$(JLIB) CustomProtocol
clean:
	$(RM) *.class
