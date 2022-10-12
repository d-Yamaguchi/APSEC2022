@API migration edit:_target.getWordsIterator(...)->_target.getEntryIterator(...)@
identifier iter;
expression _target;
identifier _VAR6;
identifier dict;
identifier _VAR3;
identifier sourceReader;
identifier _VAR2;
identifier sia;
identifier _VAR1;
identifier source;
identifier _VAR0;
AutocompleteIndexExtension autocompleter;
identifier _VAR5;
identifier autocompletefield;
identifier _VAR4;
AutocompleteIndexExtension autocompleter;
@@
- BytesRefIterator iter =_target.getWordsIterator();
+ BytesRefIterator iter = _CVAR7.getEntryIterator();

