@API migration edit:_target.termLength(...)->_target.transliterate(...)@
identifier _VAR1;
expression _target;
CyrillicTransliteratingFilter CyrillicTransliteratingFilter;
identifier _VAR0;
CharTermAttribute termAttribute;
@@
- int _VAR1 =_target.termLength();
+ CharTermAttribute _VAR0 = termAttribute;
+ int _VAR1 = CyrillicTransliteratingFilter.transliterate(_VAR0);

