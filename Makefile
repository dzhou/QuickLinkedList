JFLAGS = -g
JC = javac
MAKE = make 

all:
	$(MAKE) -C src
clean:
	$(MAKE) -C src clean 

