@API migration edit:_target.deleteDocument(...)->_target.deleteDocuments(...)@
identifier _VAR4;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier writeReader;
identifier _VAR3;
identifier _VAR1;
String idField;
identifier _VAR2;
identifier contentId;
@@
- void _VAR4 =_target.deleteDocument(_DVAR0);
+? IndexWriter writeReader = null;
+? IndexWriter _VAR0 = writeReader;
+? String _VAR1 = this.idField;
+? String contentId = this.idField;
+? String _VAR2 = contentId;
+? Term _VAR3 = new org.apache.lucene.index.Term(_VAR1, _VAR2);
+ void _VAR4 = _VAR0.deleteDocuments(_VAR3);

