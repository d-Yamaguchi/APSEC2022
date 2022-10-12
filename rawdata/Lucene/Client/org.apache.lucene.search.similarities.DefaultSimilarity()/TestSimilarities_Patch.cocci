@API migration edit:new DefaultSimilarity(...)->new ClassicSimilarity(...)@
identifier similarity;
expression _target;
@@
- DefaultSimilarity similarity =new DefaultSimilarity();
+ DefaultSimilarity similarity = new org.apache.lucene.search.similarities.ClassicSimilarity() {    @java.lang.Override    public float tf(float freq) {        return 1000.0F;    }};

