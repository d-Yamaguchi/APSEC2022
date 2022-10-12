@API migration edit:_target.termDocs(...)->_target.getTermDocsEnum(...)@
identifier docsEnum;
expression _target;
expression _DVAR0;
MultiFields MultiFields;
identifier reader;
DirectoryReader DirectoryReader;
identifier dir;
FSDirectory FSDirectory;
String indexDir;
String contentField;
identifier term;
@@
- TermDocs docsEnum =_target.termDocs(term);
+ TermDocs docsEnum = MultiFields.getTermDocsEnum(reader, null, contentField, term);

