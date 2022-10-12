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
package org.apache.mahout.classifier;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.vectorizer.encoders.ConstantValueEncoder;
import org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder;
import org.apache.mahout.vectorizer.encoders.StaticWordValueEncoder;
public final class NewsgroupHelper {
    private static final org.apache.lucene.util.Version LUCENE_VERSION = org.apache.lucene.util.Version.LUCENE_36;

    private static final java.text.SimpleDateFormat[] DATE_FORMATS = new java.text.SimpleDateFormat[]{ new java.text.SimpleDateFormat("", java.util.Locale.ENGLISH), new java.text.SimpleDateFormat("MMM-yyyy", java.util.Locale.ENGLISH), new java.text.SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", java.util.Locale.ENGLISH) };

    public static final int FEATURES = 10000;

    // 1997-01-15 00:01:00 GMT
    private static final long DATE_REFERENCE = 853286460;

    private static final long MONTH = (30 * 24) * 3600;

    private static final long WEEK = (7 * 24) * 3600;

    private final java.util.Random rand = org.apache.mahout.common.RandomUtils.getRandom();

    private final org.apache.lucene.analysis.Analyzer analyzer = new org.apache.lucene.analysis.standard.StandardAnalyzer(org.apache.mahout.classifier.NewsgroupHelper.LUCENE_VERSION);

    private final org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder encoder = new org.apache.mahout.vectorizer.encoders.StaticWordValueEncoder("body");

    private final org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder bias = new org.apache.mahout.vectorizer.encoders.ConstantValueEncoder("Intercept");

    public org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder getEncoder() {
        return encoder;
    }

    public org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder getBias() {
        return bias;
    }

    public java.util.Random getRandom() {
        return rand;
    }

    public org.apache.mahout.math.Vector encodeFeatureVector(java.io.File file, int actual, int leakType, com.google.common.collect.Multiset<java.lang.String> overallCounts) throws java.io.IOException {
        long date = ((long) (1000 * ((org.apache.mahout.classifier.NewsgroupHelper.DATE_REFERENCE + (actual * org.apache.mahout.classifier.NewsgroupHelper.MONTH)) + ((1 * org.apache.mahout.classifier.NewsgroupHelper.WEEK) * rand.nextDouble()))));
        com.google.common.collect.Multiset<java.lang.String> words = com.google.common.collect.ConcurrentHashMultiset.create();
        java.io.BufferedReader reader = com.google.common.io.Files.newReader(file, com.google.common.base.Charsets.UTF_8);
        try {
            java.lang.String line = reader.readLine();
            java.io.Reader dateString = new java.io.StringReader(org.apache.mahout.classifier.NewsgroupHelper.DATE_FORMATS[leakType % 3].format(new java.util.Date(date)));
            org.apache.mahout.classifier.NewsgroupHelper.countWords(analyzer, words, dateString, overallCounts);
            while ((line != null) && (!line.isEmpty())) {
                boolean countHeader = (((line.startsWith("From:") || line.startsWith("Subject:")) || line.startsWith("Keywords:")) || line.startsWith("Summary:")) && (leakType < 6);
                do {
                    java.io.Reader in = new java.io.StringReader(line);
                    if (countHeader) {
                        org.apache.mahout.classifier.NewsgroupHelper.countWords(analyzer, words, in, overallCounts);
                    }
                    line = reader.readLine();
                } while ((line != null) && line.startsWith(" ") );
            } 
            if (leakType < 3) {
                org.apache.mahout.classifier.NewsgroupHelper.countWords(analyzer, words, reader, overallCounts);
            }
        } finally {
            com.google.common.io.Closeables.closeQuietly(reader);
        }
        org.apache.mahout.math.Vector v = new org.apache.mahout.math.RandomAccessSparseVector(org.apache.mahout.classifier.NewsgroupHelper.FEATURES);
        bias.addToVector("", 1, v);
        for (java.lang.String word : words.elementSet()) {
            encoder.addToVector(word, java.lang.Math.log1p(words.count(word)), v);
        }
        return v;
    }

    public static void countWords(org.apache.lucene.analysis.Analyzer analyzer, java.util.Collection<java.lang.String> words, java.io.Reader in, com.google.common.collect.Multiset<java.lang.String> overallCounts) throws java.io.IOException {
        TokenStream ts = analyzer.tokenStream("text", in);
        ts.addAttribute(org.apache.lucene.analysis.tokenattributes.CharTermAttribute.class);
        ts.reset();
        __SmPLUnsupported__(0);
        overallCounts.addAll(words);
    }
}