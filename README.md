# BCC (Brainfuck to JVM bytecode compiler)

This is a compiler from Brainfuck to JVM bytecode. Pull BFCompiler.jar and use the following syntax

```
java -jar BFCompiler.jar [target source file] [name of class]
```

This command will generate a class file with the name that you indicated in the above command. To run that file, use this:

```
java -noverify [name of class]
```

We need -noverify because the compiler does not push new stack frames for every jump instruction, a safety violation according to the JVM.

Example:
```
java -jar BFCompiler.jar helloworld.bf helloworld
java -noverify helloworld
```
The above commands generate a helloworld.class file, which is executed by the second command.

Also, the brainfuck runtime requires that you press [RETURN] whenever you key in a new character. This is a limitation of the compiler. If you key in more than one character before pressing [RETURN], the runtime will simply take the first letter encountered and discard there rest.

BFRuntime.java was written as a reference for the bytecode generation for the bf runtime. Run [ASMifier](http://asm.ow2.org/doc/faq.html#Q10) to see the bytecode.


## Build instructions

This is just an Eclipse project, so just use your favourite blend and go.
To use my exact build configuration, you will need:

Bytecode Outline 2.4.3 (Eclipse Marketplace)
ANTLR 4 IDE 0.3.5 (Eclipse Marketplace)
XText 2.7.3 (download "All-In-One Update Site" [here](http://www.eclipse.org/modeling/tmf/downloads/index.php?project=xtext&showAll=1&showMax=5&sortBy=))

[Manually installing the .zip](http://stackoverflow.com/questions/23199245/eclipse-manually-install-plugin)
[Using the Eclipse Marketplace](https://wiki.eclipse.org/EPP/MPC/User_Guide)

You can use the ASMifier provided by Bytecode Outline directly from Eclipse, and saving the grammar.g4 file in /grammar will automatically build the parser/lexer.

If the versions become superseded on the Marketplace, drop and issue or even better, update this file and pull request.

To build this, create a build configuration in Eclipse that builds BFCompiler. BFRuntime contains a main method for accuracy, and your Eclipse install may default to building that so be careful.

To get the .jar file, just export as runnable JAR and you're good to go.

Have fun!