#The Makefile for the Server
#Project Chatter 2013

#Java Compliler and Flags
JFLAGS = -g
JC = javac

CLASSES = \
	ChatterServer.java \
	ChatterProtocol.java \
	ChatterRSA.java \
	ChatterServerMain.java

#Create .class files from .java files
all: $(CLASSES)
	$(JC) $(JFLAGS) *.java

keygen: ChatterRSA.class
	java ChatterRSA
clean:
	$(RM) *.class
