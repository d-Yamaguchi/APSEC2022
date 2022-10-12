private static void serialize(java.lang.String file, org.apache.lucene.util.BytesRef[] all) throws java.io.IOException {
    final java.lang.Object nothing = org.apache.lucene.util.fst.NoOutputs.getSingleton().getNoOutput();
    final org.apache.lucene.util.fst.Builder<java.lang.Object> builder = new org.apache.lucene.util.fst.Builder<java.lang.Object>(org.apache.lucene.util.fst.FST.INPUT_TYPE.BYTE4, org.apache.lucene.util.fst.NoOutputs.getSingleton());
    int _CVAR0 = 0;
    final org.apache.lucene.util.IntsRef intsRef = new org.apache.lucene.util.IntsRef(_CVAR0);
    for (org.apache.lucene.util.BytesRef br : all) {
        org.apache.lucene.util.UnicodeUtil.UTF8toUTF32(br, intsRef);
        builder.add(intsRef, nothing);
    }
    final org.apache.lucene.util.fst.FST<java.lang.Object> fst = builder.finish();
    final org.apache.lucene.analysis.de.compounds.OutputStreamDataOutput out = new org.apache.lucene.analysis.de.compounds.OutputStreamDataOutput(new java.io.FileOutputStream(file));
    fst.save(out);
    out.close();
}