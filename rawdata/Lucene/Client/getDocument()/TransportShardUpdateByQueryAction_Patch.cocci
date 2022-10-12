@API migration edit:_target.getDocument(...)->_target.uid(...)@
identifier uid;
expression _target;
identifier _VAR0;
identifier fieldVisitor;
@@
- Document uid =_target.getDocument();
+ JustUidFieldsVisitor fieldVisitor = new org.elasticsearch.index.fieldvisitor.JustUidFieldsVisitor();
+ JustUidFieldsVisitor _VAR0 = fieldVisitor;
+ Document uid = _VAR0.uid();

