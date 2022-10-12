@API migration edit:new LevensteinDistance(...)->new LevensteinDistance(...)@
identifier _VAR0;
expression _target;
@@
- LevensteinDistance _VAR0 =new LevensteinDistance();
+ LevensteinDistance _VAR0 = ((org.apache.lucene.search.spell.LevensteinDistance) (new org.apache.lucene.search.spell.LevensteinDistance()));

