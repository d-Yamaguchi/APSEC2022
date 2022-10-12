@API migration edit:_target.setIndexed(...)->_target.setIndexOptions(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier type;
identifier _VAR1;
DOCS DOCS;
@@
- _target.setIndexed(_CVAR1);
+ FieldType type = new org.apache.lucene.document.FieldType();
+ FieldType _VAR0 = type;
+ void _VAR1 = DOCS;
+  _VAR0.setIndexOptions(_VAR1)// TODO: scegliere bene
;

