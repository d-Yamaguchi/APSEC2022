@API migration edit:_target.termBuffer(...)->_target.needsTransliterating(...)@
identifier _VAR1;
expression _target;
CyrillicTransliteratingFilter CyrillicTransliteratingFilter;
identifier _VAR0;
CharTermAttribute termAttribute;
@@
- char[] _VAR1 =_target.termBuffer();
+? CharTermAttribute _VAR0 = termAttribute;
+ char[] _VAR1 = CyrillicTransliteratingFilter.needsTransliterating(_VAR0);

