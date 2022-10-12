/* Licensed to Elasticsearch under one or more contributor
license agreements. See the NOTICE file distributed with
this work for additional information regarding copyright
ownership. Elasticsearch licenses this file to you under
the Apache License, Version 2.0 (the "License"); you may
not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
package org.elasticsearch.action.updatebyquery;
/**
 * Transport action that translates the shard update by query request into a bulk request. All actions are performed
 * locally and the bulk requests are then forwarded to the replica shards (this logic is done inside
 * {@link TransportShardBulkAction} which this transport action uses).
 */
public class TransportShardUpdateByQueryAction extends org.elasticsearch.action.support.TransportAction<ShardUpdateByQueryRequest, ShardUpdateByQueryResponse> {
    public static final java.lang.String ACTION_NAME = UpdateByQueryAction.NAME + "/shard";

    private static final java.util.Set<java.lang.String> fields = new java.util.HashSet<java.lang.String>();

    static {
        fields.add(RoutingFieldMapper.NAME);
        fields.add(ParentFieldMapper.NAME);
        fields.add(TTLFieldMapper.NAME);
        fields.add(TimestampFieldMapper.NAME);
        fields.add(SourceFieldMapper.NAME);
        fields.add(UidFieldMapper.NAME);
    }

    private final org.elasticsearch.action.updatebyquery.TransportShardBulkAction bulkAction;

    private final org.elasticsearch.indices.IndicesService indicesService;

    private final org.elasticsearch.cluster.ClusterService clusterService;

    private final org.elasticsearch.script.ScriptService scriptService;

    private final int batchSize;

    private final org.elasticsearch.cache.recycler.CacheRecycler cacheRecycler;

    private final org.elasticsearch.cache.recycler.PageCacheRecycler pageCacheRecycler;

    @org.elasticsearch.common.inject.Inject
    public TransportShardUpdateByQueryAction(org.elasticsearch.common.settings.Settings settings, org.elasticsearch.threadpool.ThreadPool threadPool, TransportShardBulkAction bulkAction, org.elasticsearch.transport.TransportService transportService, org.elasticsearch.cache.recycler.CacheRecycler cacheRecycler, org.elasticsearch.indices.IndicesService indicesService, org.elasticsearch.cluster.ClusterService clusterService, org.elasticsearch.script.ScriptService scriptService, org.elasticsearch.cache.recycler.PageCacheRecycler pageCacheRecycler) {
        super(settings, threadPool);
        this.bulkAction = bulkAction;
        this.cacheRecycler = cacheRecycler;
        this.indicesService = indicesService;
        this.clusterService = clusterService;
        this.scriptService = scriptService;
        this.pageCacheRecycler = pageCacheRecycler;
        this.batchSize = componentSettings.getAsInt("bulk_size", 1000);
        transportService.registerHandler(org.elasticsearch.action.updatebyquery.TransportShardUpdateByQueryAction.ACTION_NAME, new org.elasticsearch.action.updatebyquery.TransportShardUpdateByQueryAction.TransportHandler());
    }

    protected void doExecute(final ShardUpdateByQueryRequest request, final org.elasticsearch.action.ActionListener<ShardUpdateByQueryResponse> listener) {
        java.lang.String localNodeId = clusterService.state().nodes().localNodeId();
        if (!localNodeId.equals(request.targetNodeId())) {
            throw new org.elasticsearch.ElasticsearchException("Request arrived on the wrong node. This shouldn't happen!");
        }
        if (request.operationThreaded()) {
            request.beforeLocalFork();
            threadPool.executor(ThreadPool.Names.BULK).execute(new java.lang.Runnable() {
                public void run() {
                    doExecuteInternal(request, listener);
                }
            });
        } else {
            doExecuteInternal(request, listener);
        }
    }

    private void doExecuteInternal(ShardUpdateByQueryRequest request, org.elasticsearch.action.ActionListener<ShardUpdateByQueryResponse> listener) {
        org.elasticsearch.index.service.IndexService indexService = indicesService.indexServiceSafe(request.index());
        org.elasticsearch.index.shard.service.IndexShard indexShard = indexService.shardSafe(request.shardId());
        org.elasticsearch.search.internal.ShardSearchRequest shardSearchRequest = new org.elasticsearch.search.internal.ShardSearchRequest();
        shardSearchRequest.types(request.types());
        shardSearchRequest.filteringAliases(request.filteringAliases());
        org.elasticsearch.search.internal.SearchContext searchContext = new org.elasticsearch.search.internal.DefaultSearchContext(0, shardSearchRequest, null, indexShard.acquireSearcher("update_by_query"), indexService, indexShard, scriptService, cacheRecycler, pageCacheRecycler);
        org.elasticsearch.search.internal.SearchContext.setCurrent(searchContext);
        try {
            org.elasticsearch.action.updatebyquery.UpdateByQueryContext ubqContext = parseRequestSource(indexService, request, searchContext);
            searchContext.preProcess();
            // TODO: Work per segment. The collector should collect docs per segment instead of one big set of top level ids
            org.elasticsearch.common.lucene.TopLevelFixedBitSetCollector bitSetCollector = new org.elasticsearch.common.lucene.TopLevelFixedBitSetCollector(searchContext.searcher().getIndexReader().maxDoc());
            searchContext.searcher().search(searchContext.query(), searchContext.aliasFilter(), bitSetCollector);
            org.apache.lucene.util.FixedBitSet docsToUpdate = bitSetCollector.getBitSet();
            int docsToUpdateCount = docsToUpdate.cardinality();
            logger.trace("[{}][{}] {} docs to update", request.index(), request.shardId(), docsToUpdateCount);
            if (docsToUpdateCount == 0) {
                ShardUpdateByQueryResponse response = new ShardUpdateByQueryResponse(request.shardId());
                listener.onResponse(response);
                searchContext.clearAndRelease();
                return;
            }
            org.elasticsearch.action.updatebyquery.TransportShardUpdateByQueryAction.BatchedShardUpdateByQueryExecutor bulkExecutor = new org.elasticsearch.action.updatebyquery.TransportShardUpdateByQueryAction.BatchedShardUpdateByQueryExecutor(listener, docsToUpdate, request, ubqContext);
            bulkExecutor.executeBulkIndex();
        } catch (java.lang.Throwable t) {
            // If we end up here then BatchedShardUpdateByQueryExecutor#finalizeBulkActions isn't invoked
            // so we need to release the search context.
            searchContext.clearAndRelease();
            listener.onFailure(t);
        } finally {
            org.elasticsearch.search.internal.SearchContext.removeCurrent();
        }
    }

    private org.elasticsearch.action.updatebyquery.UpdateByQueryContext parseRequestSource(org.elasticsearch.index.service.IndexService indexService, ShardUpdateByQueryRequest request, org.elasticsearch.search.internal.SearchContext context) {
        org.elasticsearch.index.query.ParsedQuery parsedQuery = null;
        java.lang.String script = null;
        java.lang.String scriptLang = null;
        java.util.Map<java.lang.String, java.lang.Object> params = org.elasticsearch.common.collect.Maps.newHashMap();
        try {
            org.elasticsearch.common.xcontent.XContentParser parser = org.elasticsearch.common.xcontent.XContentHelper.createParser(request.source());
            for (org.elasticsearch.common.xcontent.XContentParser.Token token = parser.nextToken(); token != XContentParser.Token.END_OBJECT; token = parser.nextToken()) {
                if (token == XContentParser.Token.FIELD_NAME) {
                    java.lang.String fieldName = parser.currentName();
                    if ("query".equals(fieldName)) {
                        parsedQuery = indexService.queryParserService().parse(parser);
                    } else if ("query_binary".equals(fieldName)) {
                        parser.nextToken();
                        byte[] querySource = parser.binaryValue();
                        org.elasticsearch.common.xcontent.XContentParser qSourceParser = org.elasticsearch.common.xcontent.XContentFactory.xContent(querySource).createParser(querySource);
                        parsedQuery = indexService.queryParserService().parse(qSourceParser);
                    } else if ("script".equals(fieldName)) {
                        parser.nextToken();
                        script = parser.text();
                    } else if ("lang".equals(fieldName)) {
                        parser.nextToken();
                        scriptLang = parser.text();
                    } else if ("params".equals(fieldName)) {
                        parser.nextToken();
                        params = parser.map();
                    }
                }
            }
        } catch (java.lang.Exception e) {
            throw new org.elasticsearch.ElasticsearchException("Couldn't parse query from source.", e);
        }
        if (parsedQuery == null) {
            throw new org.elasticsearch.ElasticsearchException("Query is required");
        }
        if (script == null) {
            throw new org.elasticsearch.ElasticsearchException("Script is required");
        }
        context.parsedQuery(parsedQuery);
        org.elasticsearch.script.ExecutableScript executableScript = scriptService.executable(scriptLang, script, params);
        return new org.elasticsearch.action.updatebyquery.UpdateByQueryContext(context, batchSize, clusterService.state(), script, executableScript);
    }

    class BatchedShardUpdateByQueryExecutor implements org.elasticsearch.action.ActionListener<BulkShardResponse> {
        private final org.elasticsearch.action.ActionListener<ShardUpdateByQueryResponse> finalResponseListener;

        private final org.apache.lucene.search.DocIdSetIterator iterator;

        private final int matches;

        private final org.elasticsearch.action.updatebyquery.ShardUpdateByQueryRequest request;

        private final java.util.List<BulkItemResponse> receivedBulkItemResponses;

        private final org.elasticsearch.action.updatebyquery.UpdateByQueryContext updateByQueryContext;

        // Counter for keeping tracker number of docs that have been updated.
        // No need for sync now since onResponse method in synchronized
        private int updated;

        BatchedShardUpdateByQueryExecutor(org.elasticsearch.action.ActionListener<ShardUpdateByQueryResponse> finalResponseListener, org.apache.lucene.util.FixedBitSet docsToUpdate, ShardUpdateByQueryRequest request, org.elasticsearch.action.updatebyquery.UpdateByQueryContext updateByQueryContext) {
            this.iterator = docsToUpdate.iterator();
            this.matches = docsToUpdate.cardinality();
            this.request = request;
            this.finalResponseListener = finalResponseListener;
            this.receivedBulkItemResponses = new java.util.ArrayList<BulkItemResponse>();
            this.updateByQueryContext = updateByQueryContext;
        }

        // Call can be invoked with a Network thread. Replica isn't on the same node... Therefore when
        // need to continue with the bulk do it in a new thread. One thread will enter at the time.
        public synchronized void onResponse(BulkShardResponse bulkShardResponse) {
            try {
                for (BulkItemResponse itemResponse : bulkShardResponse.getResponses()) {
                    if (!itemResponse.isFailed()) {
                        updated++;
                    }
                    switch (request.bulkResponseOptions()) {
                        case ALL :
                            receivedBulkItemResponses.add(itemResponse);
                            break;
                        case FAILED :
                            if (itemResponse.isFailed()) {
                                receivedBulkItemResponses.add(itemResponse);
                            }
                            break;
                        case NONE :
                            break;
                    }
                }
                if (iterator.docID() == org.apache.lucene.search.DocIdSetIterator.NO_MORE_DOCS) {
                    finalizeBulkActions(null);
                } else {
                    threadPool.executor(ThreadPool.Names.BULK).execute(new java.lang.Runnable() {
                        public void run() {
                            try {
                                executeBulkIndex();
                            } catch (java.lang.Throwable e) {
                                onFailure(e);
                            }
                        }
                    });
                }
            } catch (java.lang.Throwable t) {
                onFailure(t);
            }
        }

        public synchronized void onFailure(java.lang.Throwable e) {
            try {
                logger.debug("error while executing bulk operations for an update by query action, sending partial response...", e);
                finalizeBulkActions(e);
            } catch (java.lang.Throwable t) {
                finalResponseListener.onFailure(t);
            }
        }

        public void executeBulkIndex() throws java.io.IOException {
            fillBatch(iterator, updateByQueryContext.searchContext.searcher().getIndexReader(), request, updateByQueryContext.bulkItemRequestsBulkList);
            logger.trace("[{}][{}] executing bulk request with size {}", request.index(), request.shardId(), updateByQueryContext.bulkItemRequestsBulkList.size());
            if (updateByQueryContext.bulkItemRequestsBulkList.isEmpty()) {
                onResponse(new PublicBulkShardResponse(new org.elasticsearch.index.shard.ShardId(request.index(), request.shardId()), new BulkItemResponse[0]));
            } else {
                // We are already on the primary shard. Only have network traffic for replica shards
                // Also no need for threadpool b/c TransUpdateAction uses it already for local requests.
                BulkItemRequest[] bulkItemRequests = updateByQueryContext.bulkItemRequestsBulkList.toArray(new BulkItemRequest[updateByQueryContext.bulkItemRequestsBulkList.size()]);
                // We clear the list, since the array is already created
                updateByQueryContext.bulkItemRequestsBulkList.clear();
                final BulkShardRequest bulkShardRequest = new PublicBulkShardRequest(request.index(), request.shardId(), false, bulkItemRequests);
                // The batches are already threaded... No need for new thread
                bulkShardRequest.operationThreaded(false);
                bulkAction.execute(bulkShardRequest, this);
            }
        }

        private void finalizeBulkActions(java.lang.Throwable e) {
            updateByQueryContext.searchContext.clearAndRelease();
            BulkItemResponse[] bulkResponses = receivedBulkItemResponses.toArray(new BulkItemResponse[receivedBulkItemResponses.size()]);
            receivedBulkItemResponses.clear();
            ShardUpdateByQueryResponse finalResponse = new ShardUpdateByQueryResponse(request.shardId(), matches, updated, bulkResponses);
            if (e != null) {
                finalResponse.failedShardExceptionMessage(org.elasticsearch.ExceptionsHelper.detailedMessage(e));
            }
            finalResponseListener.onResponse(finalResponse);
        }

        // TODO: Work per segment. The collector should collect docs per segment instead of one big set of top level ids
        private void fillBatch(org.apache.lucene.search.DocIdSetIterator iterator, org.apache.lucene.index.IndexReader indexReader, ShardUpdateByQueryRequest request, java.util.List<BulkItemRequest> bulkItemRequests) throws java.io.IOException {
            int counter = 0;
            for (int docID = iterator.nextDoc(); docID != org.apache.lucene.search.DocIdSetIterator.NO_MORE_DOCS; docID = iterator.nextDoc()) {
                indexReader.document(docID, fieldVisitor);
                org.apache.lucene.document.DocumentStoredFieldVisitor fieldVisitor = new org.apache.lucene.document.DocumentStoredFieldVisitor(org.elasticsearch.action.updatebyquery.TransportShardUpdateByQueryAction.fields);
                JustUidFieldsVisitor fieldVisitor = new org.elasticsearch.index.fieldvisitor.JustUidFieldsVisitor();
                Document document = fieldVisitor.uid();
                int readerIndex = org.apache.lucene.index.ReaderUtil.subIndex(docID, indexReader.leaves());
                org.apache.lucene.index.AtomicReaderContext subReaderContext = indexReader.leaves().get(readerIndex);
                bulkItemRequests.add(new BulkItemRequest(counter, createRequest(request, document, subReaderContext)));
                if ((++counter) == batchSize) {
                    __SmPLUnsupported__(0);
                }
            }
        }

        // TODO: this is currently very similar to what we do in the update action, need to figure out how to nicely consolidate the two
        private org.elasticsearch.action.ActionRequest createRequest(ShardUpdateByQueryRequest request, org.apache.lucene.document.Document document, org.apache.lucene.index.AtomicReaderContext subReaderContext) {
            org.elasticsearch.index.mapper.Uid uid = org.elasticsearch.index.mapper.Uid.createUid(document.get(UidFieldMapper.NAME));
            org.apache.lucene.index.Term tUid = new org.apache.lucene.index.Term(org.elasticsearch.index.mapper.internal.UidFieldMapper.NAME, uid.toBytesRef());
            long version;
            try {
                version = org.elasticsearch.common.lucene.uid.Versions.loadVersion(subReaderContext.reader(), tUid);
            } catch (java.io.IOException e) {
                version = org.elasticsearch.common.lucene.uid.Versions.NOT_SET;
            }
            org.elasticsearch.common.bytes.BytesReference _source = new org.elasticsearch.common.bytes.BytesArray(document.getBinaryValue(SourceFieldMapper.NAME));
            java.lang.String routing = document.get(RoutingFieldMapper.NAME);
            java.lang.String parent = document.get(ParentFieldMapper.NAME);
            if (parent != null)
                parent = org.elasticsearch.index.mapper.Uid.idFromUid(parent);

            org.apache.lucene.index.IndexableField optionalField;
            optionalField = document.getField(TimestampFieldMapper.NAME);
            java.lang.Number originTimestamp = (optionalField == null) ? null : optionalField.numericValue();
            optionalField = document.getField(TTLFieldMapper.NAME);
            java.lang.Number originTtl = (optionalField == null) ? null : optionalField.numericValue();
            // Unshift TTL from timestamp
            if ((originTtl != null) && (originTimestamp != null))
                originTtl = originTtl.longValue() - originTimestamp.longValue();

            org.elasticsearch.common.collect.Tuple<org.elasticsearch.common.xcontent.XContentType, java.util.Map<java.lang.String, java.lang.Object>> sourceAndContent = org.elasticsearch.common.xcontent.XContentHelper.convertToMap(_source, true);
            final org.elasticsearch.common.xcontent.XContentType updateSourceContentType = sourceAndContent.v1();
            updateByQueryContext.scriptContext.clear();
            updateByQueryContext.scriptContext.put("_index", request.index());
            updateByQueryContext.scriptContext.put("_uid", uid.toString());
            updateByQueryContext.scriptContext.put("_type", uid.type());
            updateByQueryContext.scriptContext.put("_id", uid.id());
            updateByQueryContext.scriptContext.put("_version", version);
            updateByQueryContext.scriptContext.put("_source", sourceAndContent.v2());
            updateByQueryContext.scriptContext.put("_routing", routing);
            updateByQueryContext.scriptContext.put("_parent", parent);
            updateByQueryContext.scriptContext.put("_timestamp", originTimestamp);
            updateByQueryContext.scriptContext.put("_ttl", originTtl);
            try {
                updateByQueryContext.executableScript.setNextVar("ctx", updateByQueryContext.scriptContext);
                updateByQueryContext.executableScript.run();
                // we need to unwrap the ctx...
                updateByQueryContext.scriptContext.putAll(((java.util.Map<java.lang.String, java.lang.Object>) (updateByQueryContext.executableScript.unwrap(updateByQueryContext.scriptContext))));
            } catch (java.lang.Exception e) {
                throw new org.elasticsearch.ElasticsearchIllegalArgumentException("failed to execute script", e);
            }
            java.lang.String operation = ((java.lang.String) (updateByQueryContext.scriptContext.get("op")));
            java.lang.Object fetchedTimestamp = updateByQueryContext.scriptContext.get("_timestamp");
            java.lang.String timestamp = null;
            if (fetchedTimestamp != null) {
                if (fetchedTimestamp instanceof java.lang.String) {
                    timestamp = java.lang.String.valueOf(org.elasticsearch.common.unit.TimeValue.parseTimeValue(((java.lang.String) (fetchedTimestamp)), null).millis());
                } else {
                    timestamp = fetchedTimestamp.toString();
                }
            }
            java.lang.Object fetchedTTL = updateByQueryContext.scriptContext.get("_ttl");
            java.lang.Long ttl = null;
            if (fetchedTTL != null) {
                if (fetchedTTL instanceof java.lang.Number) {
                    ttl = ((java.lang.Number) (fetchedTTL)).longValue();
                } else {
                    ttl = org.elasticsearch.common.unit.TimeValue.parseTimeValue(((java.lang.String) (fetchedTTL)), null).millis();
                }
            }
            java.util.Map<java.lang.String, java.lang.Object> updatedSourceAsMap = ((java.util.Map<java.lang.String, java.lang.Object>) (updateByQueryContext.scriptContext.get("_source")));
            if ((operation == null) || "index".equals(operation)) {
                org.elasticsearch.action.index.IndexRequest indexRequest = org.elasticsearch.client.Requests.indexRequest(request.index()).type(uid.type()).id(uid.id()).routing(routing).parent(parent).source(updatedSourceAsMap, updateSourceContentType).version(version).replicationType(request.replicationType()).consistencyLevel(request.consistencyLevel()).timestamp(timestamp).ttl(ttl);
                indexRequest.operationThreaded(false);
                org.elasticsearch.cluster.metadata.MetaData metaData = updateByQueryContext.clusterState.metaData();
                org.elasticsearch.cluster.metadata.MappingMetaData mappingMd = null;
                if (metaData.hasIndex(indexRequest.index())) {
                    mappingMd = metaData.index(indexRequest.index()).mappingOrDefault(indexRequest.type());
                }
                java.lang.String aliasOrIndex = indexRequest.index();
                indexRequest.index(updateByQueryContext.clusterState.metaData().concreteIndex(indexRequest.index()));
                indexRequest.process(metaData, aliasOrIndex, mappingMd, false);
                return indexRequest;
            } else if ("delete".equals(operation)) {
                org.elasticsearch.action.delete.DeleteRequest deleteRequest = org.elasticsearch.client.Requests.deleteRequest(request.index()).type(uid.type()).id(uid.id()).routing(routing).parent(parent).version(version).replicationType(request.replicationType()).consistencyLevel(request.consistencyLevel());
                deleteRequest.operationThreaded(false);
                return deleteRequest;
            } else {
                logger.warn("[{}][{}] used update operation [{}] for script [{}], doing nothing...", request.index(), request.shardId(), operation, updateByQueryContext.scriptString);
                return null;
            }
        }
    }

    class TransportHandler extends org.elasticsearch.transport.BaseTransportRequestHandler<ShardUpdateByQueryRequest> {
        public org.elasticsearch.action.updatebyquery.ShardUpdateByQueryRequest newInstance() {
            return new ShardUpdateByQueryRequest();
        }

        public java.lang.String executor() {
            return ThreadPool.Names.SAME;
        }

        public void messageReceived(final ShardUpdateByQueryRequest request, final org.elasticsearch.transport.TransportChannel channel) throws java.lang.Exception {
            // no need to have a threaded listener since we just send back a response
            request.listenerThreaded(false);
            execute(request, new org.elasticsearch.action.ActionListener<ShardUpdateByQueryResponse>() {
                public void onResponse(ShardUpdateByQueryResponse result) {
                    try {
                        channel.sendResponse(result);
                    } catch (java.lang.Exception e) {
                        onFailure(e);
                    }
                }

                public void onFailure(java.lang.Throwable e) {
                    try {
                        channel.sendResponse(e);
                    } catch (java.lang.Exception e1) {
                        logger.warn("Failed to send response for get", e1);
                    }
                }
            });
        }
    }
}