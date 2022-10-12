/**
 * Copyright (c) 2008 Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.timeline;
import org.apache.lucene.search.ConstantScoreRangeQuery;
import org.codehaus.plexus.component.annotations.Component;
@org.codehaus.plexus.component.annotations.Component(role = org.sonatype.timeline.TimelineIndexer.class)
public class DefaultTimelineIndexer implements org.sonatype.timeline.TimelineIndexer {
    private static final java.lang.String TIMESTAMP = "_t";

    private static final java.lang.String TYPE = "_1";

    private static final java.lang.String SUBTYPE = "_2";

    private org.apache.lucene.store.Directory directory;

    private org.apache.lucene.index.IndexReader indexReader;

    private org.apache.lucene.index.IndexWriter indexWriter;

    private org.apache.lucene.search.IndexSearcher indexSearcher;

    public void configure(java.io.File indexDirectory) throws org.sonatype.timeline.TimelineException {
        try {
            boolean newIndex = true;
            synchronized(this) {
                if (directory != null) {
                    directory.close();
                }
                directory = org.apache.lucene.store.FSDirectory.getDirectory(indexDirectory);
                if (org.apache.lucene.index.IndexReader.indexExists(directory)) {
                    if (org.apache.lucene.index.IndexWriter.isLocked(directory)) {
                        org.apache.lucene.index.IndexWriter.unlock(directory);
                    }
                    newIndex = false;
                }
                indexWriter = new org.apache.lucene.index.IndexWriter(indexDirectory, new org.apache.lucene.analysis.KeywordAnalyzer(), newIndex, org.apache.lucene.index.IndexWriter.MaxFieldLength.LIMITED);
                closeIndexWriter();
            }
        } catch (java.lang.Exception e) {
            throw new org.sonatype.timeline.TimelineException("Fail to configure timeline index!", e);
        }
    }

    private org.apache.lucene.index.IndexWriter getIndexWriter() throws java.io.IOException {
        if (indexWriter == null) {
            indexWriter = new org.apache.lucene.index.IndexWriter(directory, new org.apache.lucene.analysis.KeywordAnalyzer(), false, org.apache.lucene.index.IndexWriter.MaxFieldLength.LIMITED);
        }
        return indexWriter;
    }

    private void closeIndexWriter() throws java.io.IOException {
        if (indexWriter != null) {
            IndexDataReadResult result = new org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult();
            Set rootGroups = new java.util.LinkedHashSet<>();
            result.setRootGroups(rootGroups);
            indexWriter.close();
            indexWriter = null;
        }
    }

    private org.apache.lucene.index.IndexReader getIndexReader() throws java.io.IOException {
        if ((indexReader == null) || (!indexReader.isCurrent())) {
            if (indexReader != null) {
                indexReader.close();
            }
            indexReader = org.apache.lucene.index.IndexReader.open(directory);
        }
        return indexReader;
    }

    private org.apache.lucene.search.IndexSearcher getIndexSearcher() throws java.io.IOException {
        if ((indexSearcher == null) || (getIndexReader() != indexSearcher.getIndexReader())) {
            if (indexSearcher != null) {
                indexSearcher.close();
                // the reader was supplied explicitly
                indexSearcher.getIndexReader().close();
            }
            indexSearcher = new org.apache.lucene.search.IndexSearcher(getIndexReader());
        }
        return indexSearcher;
    }

    private void closeIndexReaderAndSearcher() throws java.io.IOException {
        if (indexSearcher != null) {
            indexSearcher.getIndexReader().close();
            indexSearcher.close();
            indexSearcher = null;
        }
        if (indexReader != null) {
            indexReader.close();
            indexReader = null;
        }
    }

    public void add(org.sonatype.timeline.TimelineRecord record) throws org.sonatype.timeline.TimelineException {
        org.apache.lucene.index.IndexWriter writer = null;
        try {
            synchronized(this) {
                writer = getIndexWriter();
                writer.addDocument(createDocument(record));
                closeIndexWriter();
            }
        } catch (java.io.IOException e) {
            throw new org.sonatype.timeline.TimelineException("Fail to add a record to the timeline index", e);
        }
    }

    private org.apache.lucene.document.Document createDocument(org.sonatype.timeline.TimelineRecord record) {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        doc.add(new org.apache.lucene.document.Field(org.sonatype.timeline.DefaultTimelineIndexer.TIMESTAMP, org.apache.lucene.document.DateTools.timeToString(record.getTimestamp(), org.apache.lucene.document.DateTools.Resolution.MINUTE), org.apache.lucene.document.Field.Store.NO, org.apache.lucene.document.Field.Index.NOT_ANALYZED));
        doc.add(new org.apache.lucene.document.Field(org.sonatype.timeline.DefaultTimelineIndexer.TYPE, record.getType(), org.apache.lucene.document.Field.Store.NO, org.apache.lucene.document.Field.Index.NOT_ANALYZED));
        doc.add(new org.apache.lucene.document.Field(org.sonatype.timeline.DefaultTimelineIndexer.SUBTYPE, record.getSubType(), org.apache.lucene.document.Field.Store.NO, org.apache.lucene.document.Field.Index.NOT_ANALYZED));
        for (java.lang.String key : record.getData().keySet()) {
            doc.add(new org.apache.lucene.document.Field(key, record.getData().get(key), org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NOT_ANALYZED));
        }
        return doc;
    }

    private org.apache.lucene.search.Query buildQuery(long from, long to, java.util.Set<java.lang.String> types, java.util.Set<java.lang.String> subTypes) {
        if (isEmptySet(types) && isEmptySet(subTypes)) {
            return new org.apache.lucene.search.ConstantScoreRangeQuery(org.sonatype.timeline.DefaultTimelineIndexer.TIMESTAMP, org.apache.lucene.document.DateTools.timeToString(from, org.apache.lucene.document.DateTools.Resolution.MINUTE), org.apache.lucene.document.DateTools.timeToString(to, org.apache.lucene.document.DateTools.Resolution.MINUTE), true, true);
        } else {
            org.apache.lucene.search.BooleanQuery result = new org.apache.lucene.search.BooleanQuery();
            result.add(new org.apache.lucene.search.ConstantScoreRangeQuery(org.sonatype.timeline.DefaultTimelineIndexer.TIMESTAMP, org.apache.lucene.document.DateTools.timeToString(from, org.apache.lucene.document.DateTools.Resolution.MINUTE), org.apache.lucene.document.DateTools.timeToString(to, org.apache.lucene.document.DateTools.Resolution.MINUTE), true, true), org.apache.lucene.search.BooleanClause.Occur.MUST);
            if (!isEmptySet(types)) {
                org.apache.lucene.search.BooleanQuery typeQ = new org.apache.lucene.search.BooleanQuery();
                for (java.lang.String type : types) {
                    typeQ.add(new org.apache.lucene.search.TermQuery(new org.apache.lucene.index.Term(org.sonatype.timeline.DefaultTimelineIndexer.TYPE, type)), org.apache.lucene.search.BooleanClause.Occur.SHOULD);
                }
                result.add(typeQ, org.apache.lucene.search.BooleanClause.Occur.MUST);
            }
            if (!isEmptySet(subTypes)) {
                org.apache.lucene.search.BooleanQuery subTypeQ = new org.apache.lucene.search.BooleanQuery();
                for (java.lang.String subType : subTypes) {
                    subTypeQ.add(new org.apache.lucene.search.TermQuery(new org.apache.lucene.index.Term(org.sonatype.timeline.DefaultTimelineIndexer.SUBTYPE, subType)), org.apache.lucene.search.BooleanClause.Occur.SHOULD);
                }
                result.add(subTypeQ, org.apache.lucene.search.BooleanClause.Occur.MUST);
            }
            return result;
        }
    }

    private boolean isEmptySet(java.util.Set<java.lang.String> set) {
        return (set == null) || (set.size() == 0);
    }

    public java.util.List<java.util.Map<java.lang.String, java.lang.String>> retrieve(long fromTime, long toTime, java.util.Set<java.lang.String> types, java.util.Set<java.lang.String> subTypes, int from, int count, org.sonatype.timeline.TimelineFilter filter) throws org.sonatype.timeline.TimelineException {
        java.util.List<java.util.Map<java.lang.String, java.lang.String>> result = new java.util.ArrayList<java.util.Map<java.lang.String, java.lang.String>>();
        int NumberToSkip = from;
        try {
            synchronized(this) {
                org.apache.lucene.search.IndexSearcher searcher = getIndexSearcher();
                if (searcher.maxDoc() == 0) {
                    closeIndexReaderAndSearcher();
                    return result;
                }
                org.apache.lucene.search.TopFieldDocs topDocs = getIndexSearcher().search(buildQuery(fromTime, toTime, types, subTypes), null, searcher.maxDoc(), new org.apache.lucene.search.Sort(new org.apache.lucene.search.SortField(org.sonatype.timeline.DefaultTimelineIndexer.TIMESTAMP, org.apache.lucene.search.SortField.LONG, true)));
                for (int i = 0; i < topDocs.scoreDocs.length; i++) {
                    if (result.size() == count) {
                        break;
                    }
                    org.apache.lucene.document.Document doc = getIndexSearcher().doc(topDocs.scoreDocs[i].doc);
                    java.util.Map<java.lang.String, java.lang.String> data = buildData(doc);
                    if ((filter != null) && (!filter.accept(data))) {
                        continue;
                    }
                    // skip the unneeded stuff
                    if (NumberToSkip > 0) {
                        NumberToSkip--;
                        continue;
                    }
                    result.add(data);
                }
                closeIndexReaderAndSearcher();
            }
        } catch (java.io.IOException e) {
            throw new org.sonatype.timeline.TimelineException("Failed to retrieve records from the timeline index!", e);
        }
        return result;
    }

    @java.lang.SuppressWarnings("unchecked")
    private java.util.Map<java.lang.String, java.lang.String> buildData(org.apache.lucene.document.Document doc) {
        java.util.Map<java.lang.String, java.lang.String> result = new java.util.HashMap<java.lang.String, java.lang.String>();
        for (org.apache.lucene.document.Field field : ((java.util.List<org.apache.lucene.document.Field>) (doc.getFields()))) {
            if (!field.name().startsWith("_")) {
                result.put(field.name(), field.stringValue());
            }
        }
        return result;
    }

    public int purge(long fromTime, long toTime, java.util.Set<java.lang.String> types, java.util.Set<java.lang.String> subTypes) throws org.sonatype.timeline.TimelineException {
        try {
            synchronized(this) {
                closeIndexWriter();
                org.apache.lucene.search.IndexSearcher searcher = getIndexSearcher();
                if (searcher.maxDoc() == 0) {
                    closeIndexReaderAndSearcher();
                    return 0;
                }
                org.apache.lucene.search.TopDocs topDocs = searcher.search(buildQuery(fromTime, toTime, types, subTypes), searcher.maxDoc());
                for (org.apache.lucene.search.ScoreDoc scoreDoc : topDocs.scoreDocs) {
                    searcher.getIndexReader().deleteDocument(scoreDoc.doc);
                }
                closeIndexReaderAndSearcher();
                getIndexWriter().optimize();
                closeIndexWriter();
                return topDocs.scoreDocs.length;
            }
        } catch (java.io.IOException e) {
            throw new org.sonatype.timeline.TimelineException("Failed to purge records from the timeline index!", e);
        }
    }
}