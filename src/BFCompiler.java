
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class BFCompiler {
	
	public static boolean isValid (int argsLength) {
		if (argsLength < 1) {
			System.err.println("Source not specified. Exit.");
			return false;
		} else if (argsLength < 2) {
			System.err.println("Output filename not specified. Exit.");
			return false;
		}
		return true;
	}
	
	public static byte[] parse(String source, String output) throws IOException {
		ANTLRInputStream input = new ANTLRInputStream(new FileReader(source));
		BrainfuckLexer lexer = new BrainfuckLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BrainfuckParser parser = new BrainfuckParser(tokens);
        
        ParseTree tree = parser.program();
        BFListener listener = new BFListener(output);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        return listener.emitCode();
	}
	
	public static void write(byte[] code, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(name + ".class");
        fos.write(code);
        fos.close();
	}
	
	public static void main(String[] args) throws IOException {
		if (!isValid(args.length)) return;
		String source = args[0];
		String output = args[1];
        byte[] bytecode = parse(source, output);
        write(bytecode, output);
	}
}
