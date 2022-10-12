@API migration edit:_target.terms(...)->_target.getTerms(...)@
identifier t;
expression _target;
expression _DVAR0;
MultiFields MultiFields;
identifier _VAR3;
identifier reader;
DirectoryReader DirectoryReader;
identifier _VAR2;
identifier dir;
FSDirectory FSDirectory;
identifier _VAR1;
identifier _VAR0;
String indexDir;
identifier _VAR4;
String contentField;
@@
- TermEnum t =_target.terms(_DVAR0);
+? String _VAR0 = this.indexDir;
+? File _VAR1 = new java.io.File(_VAR0);
+? Directory dir = FSDirectory.open(_VAR1);
+? Directory _VAR2 = dir;
+? IndexReader reader = DirectoryReader.open(_VAR2);
+? IndexReader _VAR3 = reader;
+? String _VAR4 = contentField;
+ TermEnum t = MultiFields.getTerms(_VAR3, _VAR4);

