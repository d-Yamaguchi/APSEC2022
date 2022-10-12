@API migration edit:new Lock(...)->new Lock(...)@
identifier _VAR0;
expression _target;
@@
- Lock _VAR0 =new Lock();
+ Lock _VAR0 = new org.apache.lucene.store.Lock() {    public boolean obtain() {        return true;    }    public void release() {    }    public boolean isLocked() {        throw new java.lang.UnsupportedOperationException();    }    public java.lang.String toString() {        return "Lock@" + new org.apache.hadoop.fs.Path(directory, name);    }};

