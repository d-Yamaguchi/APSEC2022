/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nutch.analysis.lang.custom;
import java.io.IOException;
/**
 * This class runs a ngram analysis over submitted text, results might be used
 * for automatic language identifiaction.
 *
 * The similarity calculation is at experimental level. You have been warned.
 *
 * Methods are provided to build new NGramProfiles profiles.
 *
 * @author Sami Siren
 * @author Jerome Charron - http://frutch.free.fr/
 */
public class NGramProfile {
    /**
     * The minimum length allowed for a ngram.
     */
    static final int ABSOLUTE_MIN_NGRAM_LENGTH = 1;

    /**
     * The maximum length allowed for a ngram.
     */
    static final int ABSOLUTE_MAX_NGRAM_LENGTH = 4;

    /**
     * The default min length of ngram.
     */
    static final int DEFAULT_MIN_NGRAM_LENGTH = 3;

    /**
     * The default max length of ngram.
     */
    static final int DEFAULT_MAX_NGRAM_LENGTH = 3;

    /**
     * The ngram profile file extension.
     */
    static final java.lang.String FILE_EXTENSION = "ngp";

    /**
     * The profile max size (number of ngrams of the same size).
     */
    static final int MAX_SIZE = 1000;

    /**
     * separator char.
     */
    static final char SEPARATOR = '_';

    /**
     * The String form of the separator char.
     */
    private static final java.lang.String SEP_CHARSEQ = new java.lang.String(new char[]{ org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR });

    /**
     * The profile's name.
     */
    private java.lang.String name = null;

    /**
     * The NGrams of this profile sorted on the number of occurences.
     */
    private java.util.List<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> sorted = null;

    /**
     * The min length of ngram.
     */
    private int minLength = org.apache.nutch.analysis.lang.custom.NGramProfile.DEFAULT_MIN_NGRAM_LENGTH;

    /**
     * The max length of ngram.
     */
    private int maxLength = org.apache.nutch.analysis.lang.custom.NGramProfile.DEFAULT_MAX_NGRAM_LENGTH;

    /**
     * The total number of ngrams occurences.
     */
    private int[] ngramcounts = null;

    /**
     * An index of the ngrams of the profile.
     */
    private java.util.Map<java.lang.CharSequence, org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> ngrams = null;

    /**
     * A StringBuffer used during analysis.
     */
    private org.apache.nutch.analysis.lang.custom.NGramProfile.QuickStringBuffer word = new org.apache.nutch.analysis.lang.custom.NGramProfile.QuickStringBuffer();

    /**
     * Construct a new ngram profile.
     *
     * @param name
     * 		is the name of the profile
     * @param minlen
     * 		is the min length of ngram sequences
     * @param maxlen
     * 		is the max length of ngram sequences
     */
    public NGramProfile(final java.lang.String name, final int minlen, final int maxlen) {
        // TODO: Compute the initial capacity using minlen and maxlen.
        this.ngrams = new java.util.HashMap<java.lang.CharSequence, org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry>(4000);
        this.minLength = minlen;
        this.maxLength = maxlen;
        this.name = name;
    }

    /**
     *
     *
     * @return Returns the name.
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Add ngrams from a token to this profile.
     *
     * @param t
     * 		is the Token to be added
     */
    public void add(org.apache.lucene.analysis.Token t) {
        add(new java.lang.StringBuffer().append(org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR).append(termAttr.buffer()).append(org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR));
    }

    /**
     * Add ngrams from a single word to this profile.
     *
     * @param word
     * 		is the word to add
     */
    public void add(java.lang.StringBuffer word) {
        for (int i = minLength; (i <= maxLength) && (i < word.length()); i++) {
            add(word, i);
        }
    }

    /**
     * Add the last NGrams from the specified word.
     */
    private void add(org.apache.nutch.analysis.lang.custom.NGramProfile.QuickStringBuffer word) {
        int wlen = word.length();
        if (wlen >= minLength) {
            int max = java.lang.Math.min(maxLength, wlen);
            for (int i = minLength; i <= max; i++) {
                add(word.subSequence(wlen - i, wlen));
            }
        }
    }

    /**
     * Add ngrams from a single word in this profile.
     *
     * @param cs
     * 		
     */
    private void add(java.lang.CharSequence cs) {
        if (cs.equals(org.apache.nutch.analysis.lang.custom.NGramProfile.SEP_CHARSEQ)) {
            return;
        }
        org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry nge = ngrams.get(cs);
        if (nge == null) {
            nge = new org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry(cs);
            ngrams.put(cs, nge);
        }
        nge.inc();
    }

    /**
     * Analyze a piece of text.
     *
     * @param text
     * 		the text to be analyzed
     */
    public void analyze(java.lang.StringBuilder text) {
        if (ngrams != null) {
            ngrams.clear();
            sorted = null;
            ngramcounts = null;
        }
        word.clear().append(org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR);
        for (int i = 0; i < text.length(); i++) {
            char c = java.lang.Character.toLowerCase(text.charAt(i));
            if (java.lang.Character.isLetter(c)) {
                add(word.append(c));
            } else // found word boundary
            if (word.length() > 1) {
                // we have a word!
                add(word.append(org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR));
                word.clear().append(org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR);
            }
        }
        if (word.length() > 1) {
            // we have a word!
            add(word.append(org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR));
        }
        normalize();
    }

    /**
     *
     *
     * @param word
     * 		
     * @param n
     * 		sequence length
     */
    private void add(java.lang.StringBuffer word, int n) {
        for (int i = 0; i <= (word.length() - n); i++) {
            add(word.subSequence(i, i + n));
        }
    }

    /**
     * Normalize the profile (calculates the ngrams frequencies).
     */
    protected void normalize() {
        org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry e = null;
        // List sorted = getSorted();
        java.util.Iterator<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> i = ngrams.values().iterator();
        // Calculate ngramcount if not already done
        if (ngramcounts == null) {
            ngramcounts = new int[maxLength + 1];
            while (i.hasNext()) {
                e = i.next();
                ngramcounts[e.size()] += e.count;
            } 
        }
        i = ngrams.values().iterator();
        while (i.hasNext()) {
            e = i.next();
            e.frequency = ((float) (e.count)) / ((float) (ngramcounts[e.size()]));
        } 
    }

    /**
     * Return a sorted list of ngrams (sort done by 1. frequency 2. sequence)
     *
     * @return sorted vector of ngrams
     */
    public java.util.List<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> getSorted() {
        // make sure sorting is done only once
        if (sorted == null) {
            sorted = new java.util.ArrayList<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry>(ngrams.values());
            java.util.Collections.sort(sorted);
            // trim at NGRAM_LENGTH entries
            if (sorted.size() > org.apache.nutch.analysis.lang.custom.NGramProfile.MAX_SIZE) {
                sorted = sorted.subList(0, org.apache.nutch.analysis.lang.custom.NGramProfile.MAX_SIZE);
            }
        }
        return sorted;
    }

    // Inherited JavaDoc
    public java.lang.String toString() {
        java.lang.StringBuffer s = new java.lang.StringBuffer().append("NGramProfile: ").append(name).append("\n");
        java.util.Iterator<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> i = getSorted().iterator();
        while (i.hasNext()) {
            org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry entry = i.next();
            s.append("[").append(entry.seq).append("/").append(entry.count).append("/").append(entry.frequency).append("]\n");
        } 
        return s.toString();
    }

    /**
     * Calculate a score how well NGramProfiles match each other.
     *
     * @param another
     * 		ngram profile to compare against
     * @return similarity 0=exact match
     */
    public float getSimilarity(org.apache.nutch.analysis.lang.custom.NGramProfile another) {
        float sum = 0;
        try {
            java.util.Iterator<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> i = another.getSorted().iterator();
            while (i.hasNext()) {
                org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry other = i.next();
                if (ngrams.containsKey(other.seq)) {
                    sum += java.lang.Math.abs(other.frequency - ngrams.get(other.seq).frequency) / 2;
                } else {
                    sum += other.frequency;
                }
            } 
            i = getSorted().iterator();
            while (i.hasNext()) {
                org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry other = i.next();
                if (another.ngrams.containsKey(other.seq)) {
                    sum += java.lang.Math.abs(other.frequency - another.ngrams.get(other.seq).frequency) / 2;
                } else {
                    sum += other.frequency;
                }
            } 
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    /**
     * Loads a ngram profile from an InputStream
     * (assumes UTF-8 encoded content)
     *
     * @param is
     * 		the InputStream to read
     * @throws IOException
     * 		
     */
    public void load(java.io.InputStream is) throws java.io.IOException {
        ngrams.clear();
        ngramcounts = new int[maxLength + 1];
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
        java.lang.String line = null;
        while ((line = reader.readLine()) != null) {
            // # starts a comment line
            if (line.charAt(0) != '#') {
                int spacepos = line.indexOf(' ');
                java.lang.String ngramsequence = line.substring(0, spacepos).trim();
                int len = ngramsequence.length();
                if ((len >= minLength) && (len <= maxLength)) {
                    int ngramcount = java.lang.Integer.parseInt(line.substring(spacepos + 1));
                    org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry en = new org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry(ngramsequence, ngramcount);
                    ngrams.put(en.getSeq(), en);
                    ngramcounts[len] += ngramcount;
                }
            }
        } 
        normalize();
    }

    /**
     * Create a new Language profile from (preferably quite large) text file
     *
     * @param name
     * 		is thename of profile
     * @param is
     * 		is the stream to read
     * @param encoding
     * 		is the encoding of stream
     */
    public static org.apache.nutch.analysis.lang.custom.NGramProfile create(java.lang.String name, java.io.InputStream is, java.lang.String encoding) {
        org.apache.nutch.analysis.lang.custom.NGramProfile newProfile = new org.apache.nutch.analysis.lang.custom.NGramProfile(name, org.apache.nutch.analysis.lang.custom.NGramProfile.ABSOLUTE_MIN_NGRAM_LENGTH, org.apache.nutch.analysis.lang.custom.NGramProfile.ABSOLUTE_MAX_NGRAM_LENGTH);
        java.io.BufferedInputStream bis = new java.io.BufferedInputStream(is);
        byte[] buffer = new byte[4096];
        java.lang.StringBuilder text = new java.lang.StringBuilder();
        int len;
        try {
            while ((len = bis.read(buffer)) != (-1)) {
                text.append(new java.lang.String(buffer, 0, len, encoding));
            } 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        newProfile.analyze(text);
        return newProfile;
    }

    /**
     * Writes NGramProfile content into OutputStream, content is outputted with
     * UTF-8 encoding
     *
     * @param os
     * 		the Stream to output to
     * @throws IOException
     * 		
     */
    public void save(java.io.OutputStream os) throws java.io.IOException {
        // Write header
        os.write((("# NgramProfile generated at " + new java.util.Date()) + " for Nutch Language Identification\n").getBytes());
        // And then each ngram
        // First dispatch ngrams in many lists depending on their size
        // (one list for each size, in order to store MAX_SIZE ngrams for each
        // size of ngram)
        java.util.List<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> list = new java.util.ArrayList<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry>();
        java.util.List<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> sublist = new java.util.ArrayList<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry>();
        org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry[] entries = ngrams.values().toArray(new org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry[ngrams.size()]);
        for (int i = minLength; i <= maxLength; i++) {
            for (int j = 0; j < entries.length; j++) {
                if (entries[j].getSeq().length() == i) {
                    sublist.add(entries[j]);
                }
            }
            java.util.Collections.sort(sublist);
            if (sublist.size() > org.apache.nutch.analysis.lang.custom.NGramProfile.MAX_SIZE) {
                sublist = sublist.subList(0, org.apache.nutch.analysis.lang.custom.NGramProfile.MAX_SIZE);
            }
            list.addAll(sublist);
            sublist.clear();
        }
        for (int i = 0; i < list.size(); i++) {
            org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry e = list.get(i);
            java.lang.String line = ((e.toString() + " ") + e.getCount()) + "\n";
            os.write(line.getBytes("UTF-8"));
        }
        os.flush();
    }

    /**
     * main method used for testing only
     *
     * @param args
     * 		
     */
    public static void main(java.lang.String[] args) {
        java.lang.String usage = "Usage: NGramProfile " + (("[-create profilename filename encoding] " + "[-similarity file1 file2] ") + "[-score profile-name filename encoding]");
        int command = 0;
        final int CREATE = 1;
        final int SIMILARITY = 2;
        final int SCORE = 3;
        java.lang.String profilename = "";
        java.lang.String filename = "";
        java.lang.String filename2 = "";
        java.lang.String encoding = "";
        if (args.length == 0) {
            java.lang.System.err.println(usage);
            java.lang.System.exit(-1);
        }
        for (int i = 0; i < args.length; i++) {
            // parse command line
            if (args[i].equals("-create")) {
                // found -create option
                command = CREATE;
                profilename = args[++i];
                filename = args[++i];
                encoding = args[++i];
            }
            if (args[i].equals("-similarity")) {
                // found -similarity option
                command = SIMILARITY;
                filename = args[++i];
                filename2 = args[++i];
                encoding = args[++i];
            }
            if (args[i].equals("-score")) {
                // found -Score option
                command = SCORE;
                profilename = args[++i];
                filename = args[++i];
                encoding = args[++i];
            }
        }
        try {
            switch (command) {
                case CREATE :
                    java.io.File f = new java.io.File(filename);
                    java.io.FileInputStream fis = new java.io.FileInputStream(f);
                    org.apache.nutch.analysis.lang.custom.NGramProfile newProfile = org.apache.nutch.analysis.lang.custom.NGramProfile.create(profilename, fis, encoding);
                    fis.close();
                    f = new java.io.File((profilename + ".") + org.apache.nutch.analysis.lang.custom.NGramProfile.FILE_EXTENSION);
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
                    newProfile.save(fos);
                    java.lang.System.out.println(((("new profile " + profilename) + ".") + org.apache.nutch.analysis.lang.custom.NGramProfile.FILE_EXTENSION) + " was created.");
                    break;
                case SIMILARITY :
                    f = new java.io.File(filename);
                    fis = new java.io.FileInputStream(f);
                    newProfile = org.apache.nutch.analysis.lang.custom.NGramProfile.create(filename, fis, encoding);
                    newProfile.normalize();
                    f = new java.io.File(filename2);
                    fis = new java.io.FileInputStream(f);
                    org.apache.nutch.analysis.lang.custom.NGramProfile newProfile2 = org.apache.nutch.analysis.lang.custom.NGramProfile.create(filename2, fis, encoding);
                    newProfile2.normalize();
                    java.lang.System.out.println("Similarity is " + newProfile.getSimilarity(newProfile2));
                    break;
                case SCORE :
                    f = new java.io.File(filename);
                    fis = new java.io.FileInputStream(f);
                    newProfile = org.apache.nutch.analysis.lang.custom.NGramProfile.create(filename, fis, encoding);
                    f = new java.io.File((profilename + ".") + org.apache.nutch.analysis.lang.custom.NGramProfile.FILE_EXTENSION);
                    fis = new java.io.FileInputStream(f);
                    org.apache.nutch.analysis.lang.custom.NGramProfile compare = new org.apache.nutch.analysis.lang.custom.NGramProfile(profilename, org.apache.nutch.analysis.lang.custom.NGramProfile.DEFAULT_MIN_NGRAM_LENGTH, org.apache.nutch.analysis.lang.custom.NGramProfile.DEFAULT_MAX_NGRAM_LENGTH);
                    compare.load(fis);
                    java.lang.System.out.println("Score is " + compare.getSimilarity(newProfile));
                    break;
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inner class that describes a NGram
     */
    class NGramEntry implements java.lang.Comparable<org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry> {
        /**
         * The NGRamProfile this NGram is related to
         */
        private org.apache.nutch.analysis.lang.custom.NGramProfile profile = null;

        /**
         * The sequence of characters of the ngram
         */
        java.lang.CharSequence seq = null;

        /**
         * The number of occurences of this ngram in its profile
         */
        private int count = 0;

        /**
         * The frequency of this ngram in its profile
         */
        private float frequency = 0.0F;

        /**
         * Constructs a new NGramEntry
         *
         * @param seq
         * 		is the sequence of characters of the ngram
         */
        public NGramEntry(java.lang.CharSequence seq) {
            this.seq = seq;
        }

        /**
         * Constructs a new NGramEntry
         *
         * @param seq
         * 		is the sequence of characters of the ngram
         * @param count
         * 		is the number of occurences of this ngram
         */
        public NGramEntry(java.lang.String seq, int count) {
            this.seq = new java.lang.StringBuffer(seq).subSequence(0, seq.length());
            this.count = count;
        }

        /**
         * Returns the number of occurences of this ngram in its profile
         *
         * @return the number of occurences of this ngram in its profile
         */
        public int getCount() {
            return count;
        }

        /**
         * Returns the frequency of this ngram in its profile
         *
         * @return the frequency of this ngram in its profile
         */
        public float getFrequency() {
            return frequency;
        }

        /**
         * Returns the sequence of characters of this ngram
         *
         * @return the sequence of characters of this ngram
         */
        public java.lang.CharSequence getSeq() {
            return seq;
        }

        /**
         * Returns the size of this ngram
         *
         * @return the size of this ngram
         */
        public int size() {
            return seq.length();
        }

        // Inherited JavaDoc
        public int compareTo(org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry ngram) {
            int diff = java.lang.Float.compare(ngram.getFrequency(), frequency);
            if (diff != 0) {
                return diff;
            } else {
                return toString().compareTo(ngram.toString());
            }
        }

        /**
         * Increments the number of occurences of this ngram.
         */
        public void inc() {
            count++;
        }

        /**
         * Associated a profile to this ngram
         *
         * @param profile
         * 		is the profile associated to this ngram
         */
        public void setProfile(org.apache.nutch.analysis.lang.custom.NGramProfile profile) {
            this.profile = profile;
        }

        /**
         * Returns the profile associated to this ngram
         *
         * @return the profile associated to this ngram
         */
        public org.apache.nutch.analysis.lang.custom.NGramProfile getProfile() {
            return profile;
        }

        // Inherited JavaDoc
        public java.lang.String toString() {
            return seq.toString();
        }

        // Inherited JavaDoc
        public int hashCode() {
            return seq.hashCode();
        }

        // Inherited JavaDoc
        public boolean equals(java.lang.Object obj) {
            org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry ngram = null;
            try {
                ngram = ((org.apache.nutch.analysis.lang.custom.NGramProfile.NGramEntry) (obj));
                return ngram.seq.equals(seq);
            } catch (java.lang.Exception e) {
                return false;
            }
        }
    }

    private class QuickStringBuffer implements java.lang.CharSequence {
        private char[] value;

        private int count;

        QuickStringBuffer() {
            this(16);
        }

        QuickStringBuffer(char[] value) {
            this.value = value;
            count = value.length;
        }

        QuickStringBuffer(int length) {
            value = new char[length];
        }

        QuickStringBuffer(java.lang.String str) {
            this(str.length() + 16);
            append(str);
        }

        public int length() {
            return count;
        }

        private void expandCapacity(int minimumCapacity) {
            int newCapacity = (value.length + 1) * 2;
            if (newCapacity < 0) {
                newCapacity = java.lang.Integer.MAX_VALUE;
            } else if (minimumCapacity > newCapacity) {
                newCapacity = minimumCapacity;
            }
            char[] newValue = new char[newCapacity];
            java.lang.System.arraycopy(value, 0, newValue, 0, count);
            value = newValue;
        }

        org.apache.nutch.analysis.lang.custom.NGramProfile.QuickStringBuffer clear() {
            count = 0;
            return this;
        }

        public char charAt(int index) {
            return value[index];
        }

        org.apache.nutch.analysis.lang.custom.NGramProfile.QuickStringBuffer append(java.lang.String str) {
            if (str == null) {
                str = java.lang.String.valueOf(str);
            }
            int len = str.length();
            int newcount = count + len;
            if (newcount > value.length) {
                expandCapacity(newcount);
            }
            str.getChars(0, len, value, count);
            count = newcount;
            return this;
        }

        org.apache.nutch.analysis.lang.custom.NGramProfile.QuickStringBuffer append(char c) {
            int newcount = count + 1;
            if (newcount > value.length) {
                expandCapacity(newcount);
            }
            value[count++] = c;
            return this;
        }

        public java.lang.CharSequence subSequence(int start, int end) {
            return new java.lang.String(value, start, end - start);
        }

        public java.lang.String toString() {
            return new java.lang.String(this.value);
        }
    }
}