@API migration edit:_target.reopen(...)->_target.openIfChanged(...)@
identifier newReader;
expression _target;
DirectoryReader DirectoryReader;
identifier _VAR3;
identifier oldReader;
identifier _VAR2;
identifier searcher;
identifier _VAR0;
identifier _VAR1;
identifier key;
@@
- IndexReader newReader =_target.reopen();
+ DirectoryReader oldReader = ((org.apache.lucene.index.DirectoryReader) (_CVAR2.getIndexReader()));
+ DirectoryReader _VAR3 = oldReader;
+ IndexReader newReader = DirectoryReader.openIfChanged(_VAR3);

