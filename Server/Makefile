#The Makefile for the Server
#Project Chatter 2013

#Java Compliler and Flags
JFLAGS = -g
JC = javac
<<<<<<< HEAD
JLIB = Chatter.jar
=======
JLIB = Chatter.jar 
#RXTXcomm.jar
>>>>>>> 5b1063c081bcc7223e0ba9d0bd78a3520c43c773

CLASSES = \
	CustomProtocol.java\
	TwoWaySerialComm.java

#Create .class files from .java files
all: $(CLASSES)
	$(JC) $(JFLAGS) *.java -cp $(JLIB)

keygen: ChatterRSA.class
	java ChatterRSA

run: CustomProtocol.class
	java -cp .:$(JLIB):RXTXcomm.jar CustomProtocol
clean:
	$(RM) *.class
