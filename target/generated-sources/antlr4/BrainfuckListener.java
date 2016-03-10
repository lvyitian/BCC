// Generated from Brainfuck.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BrainfuckParser}.
 */
public interface BrainfuckListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(@NotNull BrainfuckParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(@NotNull BrainfuckParser.LoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull BrainfuckParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull BrainfuckParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull BrainfuckParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull BrainfuckParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(@NotNull BrainfuckParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(@NotNull BrainfuckParser.OperationContext ctx);
}