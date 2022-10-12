@API migration edit:new NGramDistance(...)->new NGramDistance(...)@
identifier _VAR1;
expression _target;
expression _DVAR0;
@@
- NGramDistance _VAR1 =new NGramDistance(_DVAR0);
+ NGramDistance _VAR1 = ((org.apache.lucene.search.spell.NGramDistance) (new org.apache.lucene.search.spell.NGramDistance(_DVAR0)));

