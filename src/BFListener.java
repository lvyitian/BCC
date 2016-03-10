import org.objectweb.asm.Opcodes;

public class BFListener extends BrainfuckBaseListener implements Opcodes {
	
	private BFBytecodeGenerator generator;
	
	public BFListener(String name) {
		generator = new BFBytecodeGenerator(name);
	}

	public byte[] emitCode() {
		return generator.emitCode();
	}
	
	@Override
	public void enterProgram(BrainfuckParser.ProgramContext ctx) {
		generator.emitClassDefnAndConstructor();
		generator.emitEnsureCapacityMethod();
		generator.emitGetMethod();
		generator.emitSetMethod();
		generator.emitIncrMethod();
		generator.emitDecrMethod();
		generator.emitPrintMethod();
		generator.emitMainAndVars();
	}
	
	@Override
	public void exitProgram(BrainfuckParser.ProgramContext ctx) {
		generator.emitMainClose();
	}
	
	@Override
	public void enterLoop(BrainfuckParser.LoopContext ctx) {
		generator.emitLoopStart();
	}
	
	@Override
	public void exitLoop(BrainfuckParser.LoopContext ctx) {
		generator.emitLoopEnd();
	}
	
	@Override
	public void enterOperation(BrainfuckParser.OperationContext ctx) {
		char op = ctx.getText().charAt(0); // just first char
		switch (op) {
			case '>':
				generator.emitRightShift();
				break;
			case '<':
				generator.emitLeftShift();
				break;
			case '+':
				generator.emitIncrement();
				break;
			case '-':
				generator.emitDecrement();
				break;
			case '.':
				generator.emitPrint();
				break;
			case ',':
				generator.emitRead();
				break;
		}
	}
}
