import java.io.IOException;
import java.util.Stack;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
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

	public BFCompiler() {
		cw = new ClassWriter(0);
		labelStack = new Stack<Label>();
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
			cw.visit(52, ACC_PUBLIC + ACC_SUPER, "BFRuntime", null, "java/lang/Object", null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "LBFRuntime;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		// emit wrap method
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "wrap", "(I)I", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitVarInsn(ILOAD, 0);
			mv.visitIntInsn(SIPUSH, 256);
			mv.visitInsn(IADD);
			mv.visitIntInsn(SIPUSH, 256);
			mv.visitInsn(IREM);
			mv.visitInsn(IRETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("i", "I", null, l0, l1, 0);
			mv.visitMaxs(2, 1);
			mv.visitEnd();
		}
		// emit main method and initializations
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
			mv.visitCode();
			Label l0 = scopeStart();
			mv.visitLabel(l0);
			// emit tape
			mv.visitIntInsn(SIPUSH, 256);
			mv.visitIntInsn(NEWARRAY, T_INT);
			mv.visitVarInsn(ASTORE, 1);
			// emit ptr
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
		mv.visitInsn(IALOAD);
		mv.visitJumpInsn(IFNE, lbody);
	}
	
	@Override
	public void enterOperation(BrainfuckParser.OperationContext ctx) {
		// TODO Auto-generated method stub
		super.exitOperation(ctx);
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
		}
		String source = args[0];
		ANTLRFileStream input = new ANTLRFileStream(source);
		BrainfuckLexer lexer = new BrainfuckLexer((CharStream) input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BrainfuckParser parser = new BrainfuckParser(tokens);
        
        ParseTree tree = parser.program();

		
	}
}
