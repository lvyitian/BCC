import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
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
	
	private void emitcapaMethod() {
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "ensureCapacity", "(ILjava/util/ArrayList;)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)V", null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "size", "()I", false);
		mv.visitVarInsn(ISTORE, 2);
		Label l1 = new Label();
		mv.visitLabel(l1);
		Label l2 = new Label();
		mv.visitJumpInsn(GOTO, l2);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.INTEGER}, 0, null);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitInsn(ICONST_0);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
		mv.visitInsn(POP);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitIincInsn(2, 1);
		mv.visitLabel(l2);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitJumpInsn(IF_ICMPLE, l3);
		Label l5 = new Label();
		mv.visitLabel(l5);
		mv.visitInsn(RETURN);
		Label l6 = new Label();
		mv.visitLabel(l6);
		mv.visitLocalVariable("index", "I", null, l0, l6, 0);
		mv.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", l0, l6, 1);
		mv.visitLocalVariable("i", "I", null, l1, l5, 2);
		mv.visitMaxs(2, 3);
		mv.visitEnd();
	}
	private void emitgetMethod() {
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "get", "(ILjava/util/ArrayList;)B", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)B", null);
		mv.visitCode();
		Label start = new Label();
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESTATIC, name, "ensureCapacity", "(ILjava/util/ArrayList;)V", false);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "get", "(I)Ljava/lang/Object;", false);
		mv.visitTypeInsn(CHECKCAST, "java/lang/Byte");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false);
		mv.visitInsn(IRETURN);
		Label end = new Label();
		mv.visitLabel(end);
		mv.visitLocalVariable("index", "I", null, start, end, 0);
		mv.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		mv.visitMaxs(2, 2);
		mv.visitEnd();
	}
	private void emitincrMethod() {
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "incr", "(ILjava/util/ArrayList;)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)V", null);
		mv.visitCode();
		Label start = new Label();
		mv.visitLabel(start);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESTATIC, name, "get", "(ILjava/util/ArrayList;)B", false);
		mv.visitVarInsn(ISTORE, 2);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IADD);
		mv.visitInsn(I2B);
		mv.visitInsn(DUP);
		mv.visitVarInsn(ISTORE, 2);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", false);
		mv.visitInsn(POP);
		mv.visitInsn(RETURN);
		Label end = new Label();
		mv.visitLabel(end);
		mv.visitLocalVariable("index", "I", null, start, end, 0);
		mv.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		mv.visitLocalVariable("current", "B", null, start, end, 2);
		mv.visitMaxs(4, 3);
		mv.visitEnd();
	}
	
	private void emitdecrMethod() {
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "decr", "(ILjava/util/ArrayList;)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)V", null);
		mv.visitCode();
		Label start = new Label();
		mv.visitLabel(start);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESTATIC, name, "get", "(ILjava/util/ArrayList;)B", false);
		mv.visitVarInsn(ISTORE, 2);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(ISUB);
		mv.visitInsn(I2B);
		mv.visitInsn(DUP);
		mv.visitVarInsn(ISTORE, 2);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", false);
		mv.visitInsn(POP);
		mv.visitInsn(RETURN);
		Label end = new Label();
		mv.visitLabel(end);
		mv.visitLocalVariable("index", "I", null, start, end, 0);
		mv.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		mv.visitLocalVariable("current", "B", null, start, end, 2);
		mv.visitMaxs(4, 3);
		mv.visitEnd();
	}
	
	private void emitprintMethod() {
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "print", "(ILjava/util/ArrayList;)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)V", null);
		mv.visitCode();
		Label start = new Label();
		mv.visitLabel(start);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESTATIC, name, "get", "(ILjava/util/ArrayList;)B", false);
		mv.visitVarInsn(ISTORE, 2);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLineNumber(28, l1);
		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitVarInsn(ILOAD, 2);
		mv.visitInsn(I2C);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(C)V", false);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLineNumber(29, l2);
		mv.visitInsn(RETURN);
		Label end = new Label();
		mv.visitLabel(end);
		mv.visitLocalVariable("index", "I", null, start, end, 0);
		mv.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		mv.visitLocalVariable("current", "B", null, start, end, 2);
		mv.visitMaxs(5, 3);
		mv.visitEnd();
	}
	private void emitsetMethod() {
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "set", "(ILjava/util/ArrayList;B)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;B)V", null);
		mv.visitCode();
		Label start = new Label();
		mv.visitLabel(start);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESTATIC, name, "ensureCapacity", "(ILjava/util/ArrayList;)V", false);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", false);
		mv.visitInsn(POP);
		mv.visitInsn(RETURN);
		Label end = new Label();
		mv.visitLabel(end);
		mv.visitLocalVariable("index", "I", null, start, end, 0);
		mv.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		mv.visitLocalVariable("item", "B", null, start, end, 2);
		mv.visitMaxs(3, 3);
		mv.visitEnd();
	}
	@Override
	public void enterProgram(BrainfuckParser.ProgramContext ctx) {
		// emit class definition and default constructor
		{
			cw.visit(52, ACC_PUBLIC + ACC_SUPER, name, null, "java/lang/Object", null);
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
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
		emitcapaMethod();
		emitgetMethod();
		emitsetMethod();
		emitincrMethod();
		emitdecrMethod();
		emitprintMethod();
		// emit main method and initializations
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
			mv.visitCode();
			Label start = scopeStart();
			mv.visitLabel(start);
			// emit arraylist decl
			mv.visitTypeInsn(NEW, "java/util/ArrayList");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 1);
			// emit ptr
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 2);
			// emit scannner
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
		Label start = scopeEnd();
		Label end = new Label();
		mv.visitLabel(end);
		mv.visitLocalVariable("args", "[Ljava/lang/String;", null, start, end, 0);
		mv.visitLocalVariable("tape", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
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
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESTATIC, name, "get", "(ILjava/util/ArrayList;)B", false);
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
				mv.visitVarInsn(ILOAD, 2);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKESTATIC, name, "incr", "(ILjava/util/ArrayList;)V", false);
				break;
			case '-':
				mv.visitVarInsn(ILOAD, 2);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKESTATIC, name, "decr", "(ILjava/util/ArrayList;)V", false);
				break;
			case '.':
				mv.visitVarInsn(ILOAD, 2);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKESTATIC, name, "print", "(ILjava/util/ArrayList;)V", false);
				break;
			case ',':
				mv.visitVarInsn(ILOAD, 2);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitVarInsn(ALOAD, 3);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Scanner", "next", "()Ljava/lang/String;", false);
				mv.visitInsn(ICONST_0);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
				mv.visitInsn(I2B);
				mv.visitMethodInsn(INVOKESTATIC, name, "set", "(ILjava/util/ArrayList;B)V", false);
				break;
		}
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
		ANTLRInputStream input = new ANTLRInputStream(new FileReader(source));
		BrainfuckLexer lexer = new BrainfuckLexer(input);
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
