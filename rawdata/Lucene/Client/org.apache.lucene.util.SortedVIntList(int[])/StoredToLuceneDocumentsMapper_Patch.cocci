@API migration edit:new SortedVIntList(...)->new OpenBitSet(...)@
identifier obs;
expression _target;
expression _DVAR0;
identifier _VAR0;
int[] sortedLuceneIds;
@@
- SortedVIntList obs =new SortedVIntList(_DVAR0);
+ int _VAR0 = sortedLuceneIds.length;
+ SortedVIntList obs = new org.apache.lucene.util.OpenBitSet(_VAR0);

