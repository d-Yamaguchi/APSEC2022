@API migration edit:new QueryParser(...)->new QueryParser(...)@
identifier parser;
expression _target;
expression _DVAR0;
identifier _VAR0;
Version Version;
identifier _VAR1;
identifier _VAR3;
identifier _VAR2;
Version Version;
@@
- QueryParser parser =new QueryParser(_DVAR0);
+ Version _VAR0 = org.apache.lucene.util.Version.LUCENE_CURRENT;
+ QueryParser parser = new org.apache.lucene.queryParser.QueryParser(_VAR0, _CVAR0, _CVAR2);

