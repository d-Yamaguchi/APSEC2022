package org.apache.lucene.analysis.de.compounds;
/**
 * Compile an FSA from an UTF-8 text file (must be properly sorted).
 */
public class CompileCompoundDictionaries {
    public static void main(java.lang.String[] args) throws java.lang.Exception {
        if (args.length < 1) {
            java.lang.System.out.println("Args: input1.txt input2.txt ...");
            java.lang.System.exit(-1);
        }
        final java.util.HashSet<org.apache.lucene.util.BytesRef> words = new java.util.HashSet<org.apache.lucene.util.BytesRef>();
        for (int i = 0; i < args.length; i++) {
            int count = 0;
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(args[i]), "UTF-8"));
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\s+");
            java.lang.String line;
            java.lang.String last = null;
            java.lang.StringBuilder buffer = new java.lang.StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.indexOf('#') >= 0)
                    continue;

                line = pattern.split(line)[0].trim();
                line = line.toLowerCase();
                if (line.equals(last))
                    continue;

                last = line;
                /* Add the word to the hash set in left-to-right characters order and reversed
                for easier matching later on.
                 */
                buffer.setLength(0);
                buffer.append(line);
                final int len = buffer.length();
                buffer.append(GermanCompoundSplitter.LTR_SYMBOL);
                words.add(new org.apache.lucene.util.BytesRef(buffer));
                buffer.setLength(len);
                buffer.reverse().append(GermanCompoundSplitter.RTL_SYMBOL);
                words.add(new org.apache.lucene.util.BytesRef(buffer));
                if (((++count) % 100000) == 0)
                    java.lang.System.err.println("Line: " + count);

            } 
            reader.close();
            java.lang.System.out.println(java.lang.String.format("%s, words: %d", args[i], count));
        }
        final org.apache.lucene.util.BytesRef[] all = new org.apache.lucene.util.BytesRef[words.size()];
        words.toArray(all);
        java.util.Arrays.sort(all, org.apache.lucene.util.BytesRef.getUTF8SortedAsUnicodeComparator());
        org.apache.lucene.analysis.de.compounds.CompileCompoundDictionaries.serialize("src/main/resources/words.fst", all);
    }

    private static void serialize(java.lang.String file, org.apache.lucene.util.BytesRef[] all) throws java.io.IOException {
        final java.lang.Object nothing = org.apache.lucene.util.fst.NoOutputs.getSingleton().getNoOutput();
        final org.apache.lucene.util.fst.Builder<java.lang.Object> builder = new org.apache.lucene.util.fst.Builder<java.lang.Object>(org.apache.lucene.util.fst.FST.INPUT_TYPE.BYTE4, org.apache.lucene.util.fst.NoOutputs.getSingleton());
        IntsRef intsRef = new org.apache.lucene.util.IntsRefBuilder();
        for (org.apache.lucene.util.BytesRef br : all) {
            org.apache.lucene.util.UnicodeUtil.UTF8toUTF32(br, intsRef);
            builder.add(intsRef, nothing);
        }
        final org.apache.lucene.util.fst.FST<java.lang.Object> fst = builder.finish();
        final org.apache.lucene.analysis.de.compounds.OutputStreamDataOutput out = new org.apache.lucene.analysis.de.compounds.OutputStreamDataOutput(new java.io.FileOutputStream(file));
        fst.save(out);
        out.close();
    }
}