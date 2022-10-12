@API migration edit:new Collector(...)->new SimpleCollector(...)@
identifier _VAR3;
expression _target;
@@
- Collector _VAR3 =new Collector();
+ Collector _VAR3 = new org.apache.lucene.search.SimpleCollector() {    private org.apache.lucene.search.Scorer scorer;    @java.lang.Override    public void collect(int doc) throws java.io.IOException {        scores[0] = scorer.score();    }    @java.lang.Override    public void setScorer(org.apache.lucene.search.Scorer scorer) {        this.scorer = scorer;    }    @java.lang.Override    public boolean acceptsDocsOutOfOrder() {        return true;    }};

