#The Makefile for the Server
#Project Chatter 2013

#Java Compliler and Flags
JFLAGS = -g
JC = javac
JLIB = Chatter.jar 

CLASSES = \
	CustomProtocol.java\
	#TwoWaySerialComm.java

#Create .class files from .java files
all: $(CLASSES)
	$(JC) $(JFLAGS) *.java -cp $(JLIB)

keygen: ChatterRSA.class
	java ChatterRSA

run: CustomProtocol.class
	java CustomProtocol -cp .:$(JLIB):RXTXcomm.jar
clean:
	$(RM) *.class
