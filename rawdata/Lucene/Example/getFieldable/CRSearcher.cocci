@API migration edit:_target.getFieldable(...)->_target.getField(...)@
identifier _VAR5;
expression _target;
expression _DVAR0;
identifier _VAR3;
identifier doc;
identifier _VAR1;
IndexSearcher searcher;
identifier _VAR2;
identifier currentDoc;
identifier hits;
identifier tdocs;
TopDocsCollector ttcollector;
int start;
int count;
identifier i;
@@
- Fieldable _VAR5 =_target.getFieldable(_DVAR0);
+? IndexSearcher _VAR1 = searcher;
+? TopDocs tdocs = ttcollector.topDocs(start, count);
+? ScoreDoc[] hits = tdocs.scoreDocs;
+? int i = 0;
+? ScoreDoc currentDoc = hits[i];
+? int _VAR2 = currentDoc.doc;
+? Document doc = _VAR1.doc(_VAR2);
+? Document _VAR3 = doc;
+ Fieldable _VAR5 = _VAR3.getField(_DVAR0);

