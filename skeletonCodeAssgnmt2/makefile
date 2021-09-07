
# See: https://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html

JAVAC = javac
JAVA = java
APP_ENTRY_CLASS = src/WordApp.java
APP_ENTRY_CLASS_COMPILED = $(basename $(APP_ENTRY_CLASS))

default: build

build: $(APP_ENTRY_CLASS)
	$(JAVAC) -d bin $(APP_ENTRY_CLASS)

run:
	$(JAVA) -cp bin $(APP_ENTRY_CLASS_COMPILED) $(ARGS)

clean:
	$(RM) bin/src/*.class


