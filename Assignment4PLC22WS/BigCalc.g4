grammar BigCalcProg;

expressionStatement
        : (statement ';')+ EOF
        ;

statement
        : assignment
        | expression
        ;

assignment
        : VAR '=' expression                     # assignExp
        | VAR '=' Number                         # assignNum
        ;

expression
        : expression op=('*' | '/') expression  # mulDiv
        | expression op=('+' | '-') expression  # addSub
        | Number                                # num
        | VAR                                   # var
        | ('(')+ expression (')')+              # parentheses
        ;


VAR
        : [a-zA-Z] Digit*
        ;

Number
        : Digit* '.' Digit+
        | Digit+
        ;

Digit
        : [0-9]
        ;

WS      : [ \t\r\n\u000C]+ -> skip
        ;

COMMENT
        :   '/*' .*? '*/' -> skip
        ;

LINE_COMMENT
        : '//' ~[\r\n]* -> skip
        ;
