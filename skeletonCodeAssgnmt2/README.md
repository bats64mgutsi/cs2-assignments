# WordApp

A multithreaded word typing Java program.

## Build And Run

To build and run the program in one step simply execute the following command in the folder
containing the makefile:

```bash
$ make build run ARGS="<total words> <words on screen> <dictionary file>"
```

where:
1. \<total words\> is the number of words to be shown to the user before the game ends.
2. \<words on screen\> is the total number of words to be shown on the screen at any point in time.
3. \<dictionary file\> is the name of the path to the dictionary file to use.

For example:

```bash
make build run ARGS="10 3 example_dict.txt"
```

## Build Only

Java classes are generated into the bin folder. To generate them simply run make:

```bash
$ make
```

## Generate Docs

To generate the javadocs for the code, run the following make command:

```bash
$ make docs
```