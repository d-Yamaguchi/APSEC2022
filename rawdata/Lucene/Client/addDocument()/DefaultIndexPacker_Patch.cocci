@API migration edit:_target.addDocument(...)->_target.add(...)@
identifier _VAR9;
expression _target;
expression _DVAR0;
identifier _VAR4;
identifier updatedLegacyDocument;
DefaultIndexPacker DefaultIndexPacker;
identifier _VAR12;
identifier _VAR2;
identifier legacyDocument;
identifier _VAR10;
identifier _VAR0;
IndexReader r;
identifier _VAR11;
identifier _VAR1;
identifier i;
identifier _VAR13;
identifier _VAR3;
IndexingContext context;
identifier _VAR8;
identifier _VAR5;
DefaultIndexingContext DefaultIndexingContext;
identifier _VAR6;
DefaultIndexingContext DefaultIndexingContext;
identifier _VAR7;
Store Store;
@@
- _target.addDocument(_CVAR5);
+ int i = 0;
+ int _VAR11 = i;
+ Document legacyDocument = r.document(_VAR11);
+ Document _VAR12 = legacyDocument;
+ Document updatedLegacyDocument = DefaultIndexPacker.updateLegacyDocument(_VAR12, context);
+ Document _VAR4 = updatedLegacyDocument;
+ DefaultIndexPacker _VAR5 = org.apache.maven.index.context.DefaultIndexingContext.FLD_DESCRIPTOR;
+ DefaultIndexPacker _VAR6 = org.apache.maven.index.context.DefaultIndexingContext.FLD_DESCRIPTOR_CONTENTS;
+ Store _VAR7 = org.apache.lucene.document.Field.Store.YES;
+ StringField _VAR8 = new org.apache.lucene.document.StringField(_VAR5, _VAR6, _VAR7);
+  _VAR4.add(_VAR8);

