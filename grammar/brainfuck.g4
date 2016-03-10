grammar Brainfuck;

WS : [ \t\f\r\n] -> skip;

LSHIFT : '<';
RSHIFT : '>';
INCR : '+';
DECR : '-';
PRNT : '.';
READ : ',';
LSTART : '[';
LEND : ']';

program : expr+ EOF;

expr : loop | operation;

loop : LSTART expr+ LEND;

operation : LSHIFT | RSHIFT | INCR | DECR | PRNT | READ;

