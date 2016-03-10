grammar brainfuck;

WS : [ \t\f\r\n] -> skip;

LSHIFT : '<';
RSHIFT : '>';
INCR : '+';
DECR : '-';
PRNT : '.';
READ : ',';
LSTART : '[';
LEND : ']';

program : expr+;

expr : loop | operation;

loop : LSTART expr+ LEND;

operation : LSHIFT | RSHIFT | INCR | DECR | PRNT | READ;

