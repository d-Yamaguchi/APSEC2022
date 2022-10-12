@API migration edit:_target.getAttribute(...)->_target.addAttribute(...)@
identifier term;
expression _target;
expression _DVAR0;
identifier ts;
NexusAnalyzer nexusAnalyzer;
IndexerField indexerField;
String text;
@@
- TermAttribute term =_target.getAttribute(org.apache.lucene.analysis.tokenattributes.TermAttribute.class);
+ TermAttribute term = ts.addAttribute(org.apache.lucene.analysis.tokenattributes.TermAttribute.class);

