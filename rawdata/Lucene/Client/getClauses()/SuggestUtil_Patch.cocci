@API migration edit:_target.getClauses(...)->_target.clauses(...)@
identifier clauses;
expression _target;
identifier _VAR0;
identifier booleanQuery;
Query query;
@@
- BooleanClause[] clauses =_target.getClauses();
+ BooleanClause[] clauses = _CVAR0.clauses();

