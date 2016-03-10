import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BFCompiler extends BrainfuckBaseListener implements Opcodes {
	
	ClassWriter cw;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;
	
	Stack<Label> labelStack;
	String name;

	public BFCompiler(String name) {
		cw = new ClassWriter(0);
		labelStack = new Stack<Label>();
		this.name = name;
	}

	public byte[] emitCode() {
		return cw.toByteArray();
	}
	
	private Label scopeStart() {
		Label label = new Label();
		labelStack.push(label);
		return label;
	}
	
	private Label scopeEnd() {
		return labelStack.pop();
	}
	
	@Override
	public void enterProgram(BrainfuckParser.ProgramContext ctx) {
		// emit class definition and default constructor
		{
			cw.visit(52, ACC_PUBLIC + ACC_SUPER, name, null, "java/lang/Object", null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "L" + name + ";", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		// emit main method and initializations
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
			mv.visitCode();
			Label l0 = scopeStart();
			mv.visitLabel(l0);
			// emit tape
			mv.visitLdcInsn(new Integer(40000));
			mv.visitIntInsn(NEWARRAY, T_BYTE);
			mv.visitVarInsn(ASTORE, 1);
			// fill tape
			mv.visitVarInsn(ALOAD, 1);
			mv.visitIntInsn(BIPUSH, -128);
			mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "fill", "([BB)V", false);
			// empt ptr
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 2);
			// emit scanner
			mv.visitTypeInsn(NEW, "java/util/Scanner");
			mv.visitInsn(DUP);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
			mv.visitMethodInsn(INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);
			mv.visitVarInsn(ASTORE, 3);
		}
	}
	
	@Override
	public void exitProgram(BrainfuckParser.ProgramContext ctx) {
		mv.visitVarInsn(ALOAD, 3);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Scanner", "close", "()V", false);
		mv.visitInsn(RETURN);
		Label end = new Label();
		Label start = scopeEnd();
		mv.visitLabel(end);
		mv.visitLocalVariable("args", "[Ljava/lang/String;", null, start, end, 0);
		mv.visitLocalVariable("tape", "[I", null, start, end, 1);
		mv.visitLocalVariable("ptr", "I", null, start, end, 2);
		mv.visitLocalVariable("in", "Ljava/util/Scanner;", null, start, end, 3);
		mv.visitMaxs(3, 4);
		mv.visitEnd();
		cw.visitEnd();
	}
	
	@Override
	public void enterLoop(BrainfuckParser.LoopContext ctx) {
		Label lcon = scopeStart();
		mv.visitJumpInsn(GOTO, lcon);
		Label lbody = scopeStart();
		mv.visitLabel(lbody);
	}
	
	@Override
	public void exitLoop(BrainfuckParser.LoopContext ctx) {
		Label lbody = scopeEnd();
		Label lcon = scopeEnd();
		mv.visitLabel(lcon);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitInsn(BALOAD);
		mv.visitIntInsn(SIPUSH, 128);
		mv.visitInsn(IADD);
		mv.visitJumpInsn(IFNE, lbody);
	}
	
	@Override
	public void enterOperation(BrainfuckParser.OperationContext ctx) {
		// TODO Auto-generated method stub
		char op = ctx.getText().charAt(0); // just first char
		switch (op) {
			case '>':
				mv.visitIincInsn(2, 1);
				break;
			case '<':
				mv.visitIincInsn(2, -1);
				break;
			case '+':
				mv.visitVarInsn(ALOAD, 1);
				mv.visitVarInsn(ILOAD, 2);
				mv.visitInsn(DUP2);
				mv.visitInsn(BALOAD);
				mv.visitInsn(ICONST_1);
				mv.visitInsn(IADD);
				mv.visitInsn(I2B);
				mv.visitInsn(BASTORE);
				break;
			case '-':
				mv.visitVarInsn(ALOAD, 1);
				mv.visitVarInsn(ILOAD, 2);
				mv.visitInsn(DUP2);
				mv.visitInsn(BALOAD);
				mv.visitInsn(ICONST_1);
				mv.visitInsn(ISUB);
				mv.visitInsn(I2B);
				mv.visitInsn(BASTORE);
				break;
			case '.':
				mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
				mv.visitVarInsn(ALOAD, 1);
				mv.visitVarInsn(ILOAD, 2);
				mv.visitInsn(BALOAD);
				mv.visitIntInsn(SIPUSH, 128);
				mv.visitInsn(IADD);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
				break;
			case ',':
				mv.visitVarInsn(ALOAD, 1);
				mv.visitVarInsn(ILOAD, 2);
				mv.visitVarInsn(ALOAD, 3);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Scanner", "nextInt", "()I", false);
				mv.visitIntInsn(BIPUSH, 127);
				mv.visitInsn(ISUB);
				mv.visitInsn(I2B);
				mv.visitInsn(BASTORE);
				break;
		}
	}
	
	@Override
	public void exitOperation(BrainfuckParser.OperationContext ctx) {
		// TODO Auto-generated method stub
		super.exitOperation(ctx);
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("Source not specified. Exit.");
			return;
		} else if (args.length < 2) {
			System.err.println("Output filename not specified. Exit.");
			return;
		}
		String source = args[0];
		ANTLRFileStream input = new ANTLRFileStream(source);
		BrainfuckLexer lexer = new BrainfuckLexer((CharStream) input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BrainfuckParser parser = new BrainfuckParser(tokens);
        
        ParseTree tree = parser.program();
        BFCompiler listener = new BFCompiler(args[1]);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        
        FileOutputStream fos = new FileOutputStream(args[1] + ".class");
        fos.write(listener.emitCode());
        fos.close();
	}
}
