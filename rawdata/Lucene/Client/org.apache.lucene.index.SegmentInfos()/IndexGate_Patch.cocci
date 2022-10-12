@API migration edit:new SegmentInfos(...)->new SegmentInfos(...)@
identifier infos;
expression _target;
@@
- SegmentInfos infos =new SegmentInfos();
+ SegmentInfos infos = new org.apache.lucene.index.SegmentInfos();

