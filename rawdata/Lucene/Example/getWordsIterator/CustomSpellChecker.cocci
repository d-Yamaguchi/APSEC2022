@API migration edit:_target.getWordsIterator(...)->_target.getEntryIterator(...)@
identifier iter;
expression _target;
Dictionary dict;
@@
- BytesRefIterator iter =_target.getWordsIterator();
+ BytesRefIterator iter = dict.getEntryIterator();

