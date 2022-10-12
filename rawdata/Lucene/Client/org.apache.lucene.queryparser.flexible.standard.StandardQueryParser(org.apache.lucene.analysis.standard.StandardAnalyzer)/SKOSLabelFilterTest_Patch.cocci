@API migration edit:new StandardQueryParser(...)->new QueryParser(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
identifier _VAR0;
Version Version;
identifier _VAR1;
identifier _VAR2;
@@
- StandardQueryParser _VAR3 =new StandardQueryParser(_DVAR0);
+ Version _VAR0 = org.apache.lucene.util.Version.LUCENE_36;
+ String _VAR1 = "content";
+ StandardQueryParser _VAR3 = new org.apache.lucene.queryParser.QueryParser(_VAR0, _VAR1, _CVAR0);

