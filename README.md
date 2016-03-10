# BCC (Brainfuck to JVM bytecode compiler)

This is a compiler from Brainfuck to JVM. Pull BFCompiler.jar and use the following syntax

java -jar BFCompiler.jar <target source file> <name of class>

This command will generate a class file with the name that you indicated in the above command. To run that file, use this:

java -noverify <name of class>

We need -noverify because the compiler does not push new stack frames for every jump instruction, a safety violation according to the JVM.

Example:

java -jar BFCompiler.jar helloworld.bf helloworld
java -noverify helloworld

The above commands generate a helloworld.class file, which is executed by the second command.

Also, the brainfuck runtime requires that you press <RETURN> whenever you key in a new character. This is a limitation of the compiler. If you key in more than one character before pressing <RETURN>, the runtime will simply take the first letter encountered and discard there rest.