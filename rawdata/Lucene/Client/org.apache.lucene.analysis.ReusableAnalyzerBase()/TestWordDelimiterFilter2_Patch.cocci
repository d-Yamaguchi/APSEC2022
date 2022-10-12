@API migration edit:new ReusableAnalyzerBase(...)->new Analyzer(...)@
identifier a;
expression _target;
@@
- ReusableAnalyzerBase a =new ReusableAnalyzerBase();
+ ReusableAnalyzerBase a = new org.apache.lucene.analysis.Analyzer() {    @java.lang.Override    public org.apache.lucene.analysis.miscellaneous.TokenStreamComponents createComponents(java.lang.String field, java.io.Reader reader) {        org.apache.lucene.analysis.Tokenizer tokenizer = new org.apache.lucene.analysis.MockTokenizer(reader, org.apache.lucene.analysis.MockTokenizer.WHITESPACE, false);        return new org.apache.lucene.analysis.miscellaneous.TokenStreamComponents(tokenizer, new org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2(tokenizer, flags, protWords));    }};

