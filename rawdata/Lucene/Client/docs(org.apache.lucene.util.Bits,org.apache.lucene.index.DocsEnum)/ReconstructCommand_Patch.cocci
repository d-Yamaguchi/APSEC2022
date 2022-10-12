@API migration edit:_target.docs(...)->_target.postings(...)@
identifier _VAR7;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR4;
identifier te;
identifier _VAR11;
identifier _VAR3;
identifier terms;
identifier _VAR9;
identifier _VAR1;
identifier atomicReader;
identifier _VAR8;
identifier _VAR0;
identifier leaf;
identifier _VAR10;
identifier _VAR2;
identifier field;
identifier _VAR5;
identifier postingsEnum;
identifier _VAR6;
FREQS FREQS;
@@
- _target.docs(liveDocs,dpe);
+ PostingsEnum postingsEnum = null;
+ PostingsEnum _VAR5 = postingsEnum;
+ void _VAR6 = FREQS;
+  te.postings(_VAR5, _VAR6);

