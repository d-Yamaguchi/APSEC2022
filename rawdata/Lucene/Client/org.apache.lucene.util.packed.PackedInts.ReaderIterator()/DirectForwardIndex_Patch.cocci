@API migration edit:new ReaderIterator(...)->new LongRandomAccessIterator(...)@
identifier _VAR0;
expression _target;
@@
- ReaderIterator _VAR0 =new ReaderIterator();
+ ReaderIterator _VAR0 = new com.senseidb.compressor.idset.IdSet.LongRandomAccessIterator() {    int current = 0;    @java.lang.Override    public boolean hasNext() throws java.io.IOException {        return current < arr.length;    }    @java.lang.Override    public long next() throws java.io.IOException {        return arr[current++];    }    @java.lang.Override    public void reset() {        current = 0;    }    @java.lang.Override    public long get(int idx) throws java.io.IOException {        return arr[idx];    }    @java.lang.Override    public long numElems() {        return arr.length;    }};

