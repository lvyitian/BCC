import java.util.Stack;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BFBytecodeGenerator implements Opcodes {
	private ClassWriter classWriter;
	private MethodVisitor methodVisitor;
	
	Stack<Label> labelStack;
	String className;
	
	public BFBytecodeGenerator(String name) {
		classWriter = new ClassWriter(0);
		labelStack = new Stack<Label>();
		className = name;
	}

	public byte[] emitCode() {
		return classWriter.toByteArray();
	}
	
	private Label scopeStart() {
		Label label = new Label();
		labelStack.push(label);
		return label;
	}
	
	private Label scopeEnd() {
		return labelStack.pop();
	}
	
	public void emitClassDefnAndConstructor() {
		classWriter.visit(52, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", null);
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		methodVisitor.visitCode();
		Label l0 = new Label();
		methodVisitor.visitVarInsn(ALOAD, 0);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		methodVisitor.visitInsn(RETURN);
		Label l1 = new Label();
		methodVisitor.visitLabel(l1);
		methodVisitor.visitLocalVariable("this", "L" + className + ";", null, l0, l1, 0);
		methodVisitor.visitMaxs(1, 1);
		methodVisitor.visitEnd();
	}
	
	public void emitEnsureCapacityMethod() {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "ensureCapacity", "(ILjava/util/ArrayList;)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)V", null);
		methodVisitor.visitCode();
		Label l0 = new Label();
		methodVisitor.visitLabel(l0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "size", "()I", false);
		methodVisitor.visitVarInsn(ISTORE, 2);
		Label l1 = new Label();
		methodVisitor.visitLabel(l1);
		Label l2 = new Label();
		methodVisitor.visitJumpInsn(GOTO, l2);
		Label l3 = new Label();
		methodVisitor.visitLabel(l3);
		methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.INTEGER}, 0, null);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitInsn(ICONST_0);
		methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
		methodVisitor.visitInsn(POP);
		Label l4 = new Label();
		methodVisitor.visitLabel(l4);
		methodVisitor.visitIincInsn(2, 1);
		methodVisitor.visitLabel(l2);
		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitJumpInsn(IF_ICMPLE, l3);
		Label l5 = new Label();
		methodVisitor.visitLabel(l5);
		methodVisitor.visitInsn(RETURN);
		Label l6 = new Label();
		methodVisitor.visitLabel(l6);
		methodVisitor.visitLocalVariable("index", "I", null, l0, l6, 0);
		methodVisitor.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", l0, l6, 1);
		methodVisitor.visitLocalVariable("i", "I", null, l1, l5, 2);
		methodVisitor.visitMaxs(2, 3);
		methodVisitor.visitEnd();
	}
	
	public void emitGetMethod() {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "get", "(ILjava/util/ArrayList;)B", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)B", null);
		methodVisitor.visitCode();
		Label start = new Label();
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "ensureCapacity", "(ILjava/util/ArrayList;)V", false);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "get", "(I)Ljava/lang/Object;", false);
		methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Byte");
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false);
		methodVisitor.visitInsn(IRETURN);
		Label end = new Label();
		methodVisitor.visitLabel(end);
		methodVisitor.visitLocalVariable("index", "I", null, start, end, 0);
		methodVisitor.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		methodVisitor.visitMaxs(2, 2);
		methodVisitor.visitEnd();
	}
	
	public void emitIncrMethod() {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "incr", "(ILjava/util/ArrayList;)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)V", null);
		methodVisitor.visitCode();
		Label start = new Label();
		methodVisitor.visitLabel(start);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "get", "(ILjava/util/ArrayList;)B", false);
		methodVisitor.visitVarInsn(ISTORE, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitInsn(ICONST_1);
		methodVisitor.visitInsn(IADD);
		methodVisitor.visitInsn(I2B);
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitVarInsn(ISTORE, 2);
		methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", false);
		methodVisitor.visitInsn(POP);
		methodVisitor.visitInsn(RETURN);
		Label end = new Label();
		methodVisitor.visitLabel(end);
		methodVisitor.visitLocalVariable("index", "I", null, start, end, 0);
		methodVisitor.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		methodVisitor.visitLocalVariable("current", "B", null, start, end, 2);
		methodVisitor.visitMaxs(4, 3);
		methodVisitor.visitEnd();
	}
	
	public void emitDecrMethod() {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "decr", "(ILjava/util/ArrayList;)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)V", null);
		methodVisitor.visitCode();
		Label start = new Label();
		methodVisitor.visitLabel(start);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "get", "(ILjava/util/ArrayList;)B", false);
		methodVisitor.visitVarInsn(ISTORE, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitInsn(ICONST_1);
		methodVisitor.visitInsn(ISUB);
		methodVisitor.visitInsn(I2B);
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitVarInsn(ISTORE, 2);
		methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", false);
		methodVisitor.visitInsn(POP);
		methodVisitor.visitInsn(RETURN);
		Label end = new Label();
		methodVisitor.visitLabel(end);
		methodVisitor.visitLocalVariable("index", "I", null, start, end, 0);
		methodVisitor.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		methodVisitor.visitLocalVariable("current", "B", null, start, end, 2);
		methodVisitor.visitMaxs(4, 3);
		methodVisitor.visitEnd();
	}
	
	public void emitPrintMethod() {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "print", "(ILjava/util/ArrayList;)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;)V", null);
		methodVisitor.visitCode();
		Label start = new Label();
		methodVisitor.visitLabel(start);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "get", "(ILjava/util/ArrayList;)B", false);
		methodVisitor.visitVarInsn(ISTORE, 2);
		Label l1 = new Label();
		methodVisitor.visitLabel(l1);
		methodVisitor.visitLineNumber(28, l1);
		methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitInsn(I2C);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(C)V", false);
		Label l2 = new Label();
		methodVisitor.visitLabel(l2);
		methodVisitor.visitLineNumber(29, l2);
		methodVisitor.visitInsn(RETURN);
		Label end = new Label();
		methodVisitor.visitLabel(end);
		methodVisitor.visitLocalVariable("index", "I", null, start, end, 0);
		methodVisitor.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		methodVisitor.visitLocalVariable("current", "B", null, start, end, 2);
		methodVisitor.visitMaxs(5, 3);
		methodVisitor.visitEnd();
	}
	
	public void emitSetMethod() {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "set", "(ILjava/util/ArrayList;B)V", "(ILjava/util/ArrayList<Ljava/lang/Byte;>;B)V", null);
		methodVisitor.visitCode();
		Label start = new Label();
		methodVisitor.visitLabel(start);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "ensureCapacity", "(ILjava/util/ArrayList;)V", false);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitVarInsn(ILOAD, 0);
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", false);
		methodVisitor.visitInsn(POP);
		methodVisitor.visitInsn(RETURN);
		Label end = new Label();
		methodVisitor.visitLabel(end);
		methodVisitor.visitLocalVariable("index", "I", null, start, end, 0);
		methodVisitor.visitLocalVariable("arr", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		methodVisitor.visitLocalVariable("item", "B", null, start, end, 2);
		methodVisitor.visitMaxs(3, 3);
		methodVisitor.visitEnd();
	}
	
	public void emitMainAndVars() {
		methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
		methodVisitor.visitCode();
		Label start = scopeStart();
		methodVisitor.visitLabel(start);
		// emit arraylist decl
		methodVisitor.visitTypeInsn(NEW, "java/util/ArrayList");
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
		methodVisitor.visitVarInsn(ASTORE, 1);
		// emit ptr
		methodVisitor.visitInsn(ICONST_0);
		methodVisitor.visitVarInsn(ISTORE, 2);
		// emit scannner
		methodVisitor.visitTypeInsn(NEW, "java/util/Scanner");
		methodVisitor.visitInsn(DUP);
		methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
		methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);
		methodVisitor.visitVarInsn(ASTORE, 3);
	}
	
	public void emitMainClose() {
		methodVisitor.visitVarInsn(ALOAD, 3);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/Scanner", "close", "()V", false);
		methodVisitor.visitInsn(RETURN);
		Label start = scopeEnd();
		Label end = new Label();
		methodVisitor.visitLabel(end);
		methodVisitor.visitLocalVariable("args", "[Ljava/lang/String;", null, start, end, 0);
		methodVisitor.visitLocalVariable("tape", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Ljava/lang/Byte;>;", start, end, 1);
		methodVisitor.visitLocalVariable("ptr", "I", null, start, end, 2);
		methodVisitor.visitLocalVariable("in", "Ljava/util/Scanner;", null, start, end, 3);
		methodVisitor.visitMaxs(3, 4);
		methodVisitor.visitEnd();
		classWriter.visitEnd();
	}
	
	public void emitLoopStart() {
		Label lcon = scopeStart();
		methodVisitor.visitJumpInsn(GOTO, lcon);
		Label lbody = scopeStart();
		methodVisitor.visitLabel(lbody);
	}
	
	public void emitLoopEnd() {
		Label lbody = scopeEnd();
		Label lcon = scopeEnd();
		methodVisitor.visitLabel(lcon);
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "get", "(ILjava/util/ArrayList;)B", false);
		methodVisitor.visitJumpInsn(IFNE, lbody);
	}
	
	public void emitRightShift() {
		methodVisitor.visitIincInsn(2, 1);
	}
	
	public void emitLeftShift() {
		methodVisitor.visitIincInsn(2, -1);
	}
	
	public void emitIncrement() {
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "incr", "(ILjava/util/ArrayList;)V", false);
	}
	
	public void emitDecrement() {
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "decr", "(ILjava/util/ArrayList;)V", false);
	}
	
	public void emitPrint() {
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "print", "(ILjava/util/ArrayList;)V", false);
	}
	
	public void emitRead() {
		methodVisitor.visitVarInsn(ILOAD, 2);
		methodVisitor.visitVarInsn(ALOAD, 1);
		methodVisitor.visitVarInsn(ALOAD, 3);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/Scanner", "next", "()Ljava/lang/String;", false);
		methodVisitor.visitInsn(ICONST_0);
		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
		methodVisitor.visitInsn(I2B);
		methodVisitor.visitMethodInsn(INVOKESTATIC, className, "set", "(ILjava/util/ArrayList;B)V", false);
	}
}
