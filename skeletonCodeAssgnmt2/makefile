
# See: https://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html

JAVAC = javac
JAVA = java
JAVA_DOCS = javadoc
APP_ENTRY_CLASS = src/WordApp.java
APP_ENTRY_CLASS_COMPILED = $(basename $(APP_ENTRY_CLASS))

default: build

build: $(APP_ENTRY_CLASS)
	$(JAVAC) -d bin $(APP_ENTRY_CLASS)

run:
	$(JAVA) -cp bin $(APP_ENTRY_CLASS_COMPILED) $(ARGS)

docs:
	$(JAVA_DOCS) -d docs src

clean:
	$(RM) bin/src/*.class


