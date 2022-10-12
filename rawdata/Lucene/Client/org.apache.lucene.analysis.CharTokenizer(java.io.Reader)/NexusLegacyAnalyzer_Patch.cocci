@API migration edit:new CharTokenizer(...)->new CharTokenizer(...)@
identifier _VAR0;
expression _target;
expression _DVAR0;
Version Version;
Reader reader;
@@
- CharTokenizer _VAR0 =new CharTokenizer(_DVAR0);
+ CharTokenizer _VAR0 = new org.apache.lucene.analysis.util.CharTokenizer(org.apache.lucene.util.Version.LUCENE_46, reader) {    @java.lang.Override    protected boolean isTokenChar(int c) {        return java.lang.Character.isLetterOrDigit(c);    }    @java.lang.Override    protected int normalize(int c) {        return java.lang.Character.toLowerCase(c);    }};

