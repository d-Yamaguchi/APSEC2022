@API migration edit:new DocIdSet(...)->new MatchSomeDocsQuery(...)@
identifier _VAR0;
expression _target;
@@
- DocIdSet _VAR0 =new DocIdSet();
+ DocIdSet _VAR0 = new com.senseidb.clue.util.MatchSomeDocsQuery() {    @java.lang.Override    public java.lang.String toString(java.lang.String field) {        return null;    }    @java.lang.Override    protected boolean match(int docId) {        int guess = rand.nextInt(100);        if (guess < percentToDelete) {            return true;        }        return false;    }};

