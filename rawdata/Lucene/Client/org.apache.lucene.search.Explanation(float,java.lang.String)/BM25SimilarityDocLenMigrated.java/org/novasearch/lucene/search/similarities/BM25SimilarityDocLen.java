package org.novasearch.lucene.search.similarities;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.similarities.Similarity;
/**
 * BM25 Similarity. Introduced in Stephen E. Robertson, Steve Walker,
 * Susan Jones, Micheline Hancock-Beaulieu, and Mike Gatford. Okapi at TREC-3.
 * In Proceedings of the Third <b>T</b>ext <b>RE</b>trieval <b>C</b>onference (TREC 1994).
 * Gaithersburg, USA, November 1994.
 * <p/>
 * BM25L and BM25+ improved versions of BM25. Introduced in
 * Yuanhua Lv, ChengXiang Zhai. "Lower-Bounding Term Frequency Normalization".
 * In Proceedings of the 20th ACM International Conference on Information and
 * Knowledge Management  (CIKM'11).
 *
 * @lucene.experimental 
 */
public class BM25SimilarityDocLen extends org.apache.lucene.search.similarities.Similarity {
    public enum BM25Model {

        CLASSIC,
        L,
        PLUS;}

    private final float k1;

    private final float b;

    private final float d;

    private final org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model model;

    public BM25SimilarityDocLen(float k1, float b, float d, org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model model) {
        this.k1 = k1;
        this.b = b;
        this.d = d;
        this.model = model;
    }

    /**
     * BM25 with the supplied parameter values.
     *
     * @param k1
     * 		Controls non-linear term frequency normalization (saturation).
     * @param b
     * 		Controls to what degree document length normalizes tf values.
     */
    public BM25SimilarityDocLen(float k1, float b) {
        this.k1 = k1;
        this.b = b;
        this.d = 0;
        this.model = org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.CLASSIC;
    }

    /**
     * BM25 with these default values:
     * <ul>
     *   <li>{@code k1 = 1.2},
     *   <li>{@code b = 0.75}.</li>
     *   <li>{@code d = 0.5} for BM25L, {@code d = 1.0} for BM25PLUS.</li>
     * </ul>
     */
    public BM25SimilarityDocLen(org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model model) {
        this.k1 = 1.2F;
        this.b = 0.75F;
        if (model == org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.L) {
            this.d = 0.5F;
        } else if (model == org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.PLUS) {
            this.d = 1.0F;
        } else {
            this.d = 0;
        }
        this.model = model;
    }

    /**
     * BM25 with these default values:
     * <ul>
     *   <li>{@code k1 = 1.2},
     *   <li>{@code b = 0.75}.</li>
     * </ul>
     */
    public BM25SimilarityDocLen() {
        this.k1 = 1.2F;
        this.b = 0.75F;
        this.d = 0;
        this.model = org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.CLASSIC;
    }

    /**
     * Implemented as <code>log(1 + (numDocs - docFreq + 0.5)/(docFreq + 0.5))</code>.
     */
    protected float idf(long docFreq, long numDocs) {
        return ((float) (java.lang.Math.log(1 + (((numDocs - docFreq) + 0.5) / (docFreq + 0.5)))));
    }

    /**
     * Implemented as <code>1 / (distance + 1)</code>.
     */
    protected float sloppyFreq(int distance) {
        return 1.0F / (distance + 1);
    }

    /**
     * The default implementation returns <code>1</code>
     */
    protected float scorePayload(int doc, int start, int end, org.apache.lucene.util.BytesRef payload) {
        return 1;
    }

    /**
     * The default implementation computes the average as <code>sumTotalTermFreq / maxDoc</code>,
     * or returns <code>1</code> if the index does not store sumTotalTermFreq (Lucene 3.x indexes
     * or any field that omits frequency information).
     */
    protected float avgFieldLength(org.apache.lucene.search.CollectionStatistics collectionStats) {
        final long sumTotalTermFreq = collectionStats.sumTotalTermFreq();
        if (sumTotalTermFreq <= 0) {
            return 1.0F;// field does not exist, or stat is unsupported

        } else {
            return ((float) (sumTotalTermFreq / ((double) (collectionStats.maxDoc()))));
        }
    }

    /**
     * True if overlap tokens (tokens with a position of increment of zero) are
     * discounted from the document's length.
     */
    protected boolean discountOverlaps = true;

    /**
     * Sets whether overlap tokens (Tokens with 0 position increment) are
     *  ignored when computing norm.  By default this is true, meaning overlap
     *  tokens do not count when computing norms.
     */
    public void setDiscountOverlaps(boolean v) {
        discountOverlaps = v;
    }

    /**
     * Returns true if overlap tokens are discounted from the document's length.
     *
     * @see #setDiscountOverlaps
     */
    public boolean getDiscountOverlaps() {
        return discountOverlaps;
    }

    @java.lang.Override
    public final long computeNorm(org.apache.lucene.index.FieldInvertState state) {
        final int numTerms = (discountOverlaps) ? state.getLength() - state.getNumOverlap() : state.getLength();
        return ((int) (state.getBoost() * numTerms));
    }

    /**
     * Computes a score factor for a simple term and returns an explanation
     * for that score factor.
     *
     * <p>
     * The default implementation uses:
     *
     * <pre class="prettyprint">
     * idf(docFreq, searcher.maxDoc());
     * </pre>
     *
     * Note that {@link CollectionStatistics#maxDoc()} is used instead of
     * {@link org.apache.lucene.index.IndexReader#numDocs() IndexReader#numDocs()} because also
     * {@link TermStatistics#docFreq()} is used, and when the latter
     * is inaccurate, so is {@link CollectionStatistics#maxDoc()}, and in the same direction.
     * In addition, {@link CollectionStatistics#maxDoc()} is more efficient to compute
     *
     * @param collectionStats
     * 		collection-level statistics
     * @param termStats
     * 		term-level statistics for the term
     * @return an Explain object that includes both an idf score factor
    and an explanation for the term.
     */
    public org.apache.lucene.search.Explanation idfExplain(org.apache.lucene.search.CollectionStatistics collectionStats, org.apache.lucene.search.TermStatistics termStats) {
        final long df = termStats.docFreq();
        final long max = collectionStats.maxDoc();
        final float idf = idf(df, max);
        return new java.util.ArrayList<>();
    }

    /**
     * Computes a score factor for a phrase.
     *
     * <p>
     * The default implementation sums the idf factor for
     * each term in the phrase.
     *
     * @param collectionStats
     * 		collection-level statistics
     * @param termStats
     * 		term-level statistics for the terms in the phrase
     * @return an Explain object that includes both an idf
    score factor for the phrase and an explanation
    for each term.
     */
    public org.apache.lucene.search.Explanation idfExplain(org.apache.lucene.search.CollectionStatistics collectionStats, org.apache.lucene.search.TermStatistics[] termStats) {
        final long max = collectionStats.maxDoc();
        float idf = 0.0F;
        final org.apache.lucene.search.Explanation exp = new org.apache.lucene.search.Explanation();
        exp.setDescription("idf(), sum of:");
        for (final org.apache.lucene.search.TermStatistics stat : termStats) {
            final long df = stat.docFreq();
            final float termIdf = idf(df, max);
            exp.addDetail(new org.apache.lucene.search.Explanation(termIdf, ((("idf(docFreq=" + df) + ", maxDocs=") + max) + ")"));
            idf += termIdf;
        }
        exp.setValue(idf);
        return exp;
    }

    @java.lang.Override
    public final org.novasearch.lucene.search.similarities.SimWeight computeWeight(float queryBoost, org.apache.lucene.search.CollectionStatistics collectionStats, org.apache.lucene.search.TermStatistics... termStats) {
        org.apache.lucene.search.Explanation idf = (termStats.length == 1) ? idfExplain(collectionStats, termStats[0]) : idfExplain(collectionStats, termStats);
        float avgdl = avgFieldLength(collectionStats);
        return new org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Stats(collectionStats.field(), idf, queryBoost, avgdl);
    }

    @java.lang.Override
    public final org.novasearch.lucene.search.similarities.SimScorer simScorer(org.novasearch.lucene.search.similarities.SimWeight stats, org.apache.lucene.index.AtomicReaderContext context) throws java.io.IOException {
        org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Stats bm25stats = ((org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Stats) (stats));
        return new org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25DocScorer(bm25stats, context.reader().getNormValues(bm25stats.field));
    }

    private class BM25DocScorer extends org.novasearch.lucene.search.similarities.SimScorer {
        private final org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Stats stats;

        private final float weightValue;// boost * idf * (k1 + 1)


        private final org.apache.lucene.index.NumericDocValues norms;

        BM25DocScorer(org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Stats stats, org.apache.lucene.index.NumericDocValues norms) throws java.io.IOException {
            this.stats = stats;
            this.weightValue = stats.weight * (k1 + 1);
            this.norms = norms;
        }

        @java.lang.Override
        public float score(int doc, float freq) {
            float norm;
            if (norms == null) {
                // if there are no norms, we act as if b=0
                norm = k1;
            } else {
                float doclen = ((float) (norms.get(doc)));
                norm = (1 - b) + ((b * doclen) / stats.avgdl);
                if (model == org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.L) {
                    norm += d;
                }
                norm *= k1;
                if (model == org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.PLUS) {
                    norm += d;
                }
            }
            return (weightValue * freq) / (freq + norm);
        }

        @java.lang.Override
        public org.apache.lucene.search.Explanation explain(int doc, org.apache.lucene.search.Explanation freq) {
            return explainScore(doc, freq, stats, norms);
        }

        @java.lang.Override
        public float computeSlopFactor(int distance) {
            return sloppyFreq(distance);
        }

        @java.lang.Override
        public float computePayloadFactor(int doc, int start, int end, org.apache.lucene.util.BytesRef payload) {
            return scorePayload(doc, start, end, payload);
        }
    }

    /**
     * Collection statistics for the BM25 model.
     */
    private static class BM25Stats extends org.novasearch.lucene.search.similarities.SimWeight {
        /**
         * BM25's idf
         */
        private final org.apache.lucene.search.Explanation idf;

        /**
         * The average document length.
         */
        private final float avgdl;

        /**
         * query's inner boost
         */
        private final float queryBoost;

        /**
         * query's outer boost (only for explain)
         */
        private float topLevelBoost;

        /**
         * weight (idf * boost)
         */
        private float weight;

        /**
         * field name, for pulling norms
         */
        private final java.lang.String field;

        BM25Stats(java.lang.String field, org.apache.lucene.search.Explanation idf, float queryBoost, float avgdl) {
            this.field = field;
            this.idf = idf;
            this.queryBoost = queryBoost;
            this.avgdl = avgdl;
        }

        @java.lang.Override
        public float getValueForNormalization() {
            // we return a TF-IDF like normalization to be nice, but we don't actually normalize ourselves.
            final float queryWeight = idf.getValue() * queryBoost;
            return queryWeight * queryWeight;
        }

        @java.lang.Override
        public void normalize(float queryNorm, float topLevelBoost) {
            // we don't normalize with queryNorm at all, we just capture the top-level boost
            this.topLevelBoost = topLevelBoost;
            this.weight = (idf.getValue() * queryBoost) * topLevelBoost;
        }
    }

    private org.apache.lucene.search.Explanation explainScore(int doc, org.apache.lucene.search.Explanation freq, org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Stats stats, org.apache.lucene.index.NumericDocValues norms) {
        org.apache.lucene.search.Explanation result = new org.apache.lucene.search.Explanation();
        result.setDescription(((("score(doc=" + doc) + ",freq=") + freq) + "), product of:");
        org.apache.lucene.search.Explanation boostExpl = new org.apache.lucene.search.Explanation(stats.queryBoost * stats.topLevelBoost, "boost");
        if (boostExpl.getValue() != 1.0F)
            result.addDetail(boostExpl);

        result.addDetail(stats.idf);
        org.apache.lucene.search.Explanation tfNormExpl = new org.apache.lucene.search.Explanation();
        tfNormExpl.setDescription("tfNorm, computed from:");
        tfNormExpl.addDetail(freq);
        tfNormExpl.addDetail(new org.apache.lucene.search.Explanation(k1, "parameter k1"));
        if (model != org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.CLASSIC) {
            tfNormExpl.addDetail(new org.apache.lucene.search.Explanation(d, "parameter d"));
        }
        if (norms == null) {
            tfNormExpl.addDetail(new org.apache.lucene.search.Explanation(0, "parameter b (norms omitted for field)"));
            tfNormExpl.setValue((freq.getValue() * (k1 + 1)) / (freq.getValue() + k1));
        } else {
            float doclen = ((float) (norms.get(doc)));
            tfNormExpl.addDetail(new org.apache.lucene.search.Explanation(b, "parameter b"));
            tfNormExpl.addDetail(new org.apache.lucene.search.Explanation(stats.avgdl, "avgFieldLength"));
            tfNormExpl.addDetail(new org.apache.lucene.search.Explanation(doclen, "fieldLength"));
            float value = (1 - b) + ((b * doclen) / stats.avgdl);
            if (model == org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.L) {
                value += d;
            }
            value *= k1;
            if (model == org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.PLUS) {
                value += d;
            }
            tfNormExpl.setValue((freq.getValue() * (k1 + 1)) / (freq.getValue() + value));
        }
        result.addDetail(tfNormExpl);
        result.setValue((boostExpl.getValue() * stats.idf.getValue()) * tfNormExpl.getValue());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        if (model == org.novasearch.lucene.search.similarities.BM25SimilarityDocLen.BM25Model.CLASSIC) {
            return ((("BM25(k1=" + k1) + ",b=") + b) + ")";
        } else {
            return ((((((("BM25" + model.toString()) + "(k1=") + k1) + ",b=") + b) + ",d=") + d) + ")";
        }
    }

    /**
     * Returns the <code>k1</code> parameter
     *
     * @see #BM25SimilarityDocLen(float, float)
     */
    public float getK1() {
        return k1;
    }

    /**
     * Returns the <code>b</code> parameter
     *
     * @see #BM25SimilarityDocLen(float, float)
     */
    public float getB() {
        return b;
    }

    /**
     * Returns the <code>d</code> parameter
     *
     * @see #BM25SimilarityDocLen(float, float, float, BM25Model)
     */
    public float getD() {
        return d;
    }
}