/*******************************************************************************
 * Trombone is a flexible text processing and analysis library used
 * primarily by Voyant Tools (voyant-tools.org).
 * 
 * Copyright (©) 2007-2012 Stéfan Sinclair & Geoffrey Rockwell
 * 
 * This file is part of Trombone.
 * 
 * Trombone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Trombone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Trombone.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.voyanttools.trombone.lucene;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;

import org.apache.lucene.util.OpenBitSet;
import org.apache.lucene.util.OpenBitSetIterator;
import org.voyanttools.trombone.storage.Storage;

/**
 * @author sgs
 *
 */
public class StoredToLuceneDocumentsMapper {

//	private String[] documentIds;
	private Map<Integer, Integer> luceneIds;
	private Map<String, Integer> corpusIds;
	private int[] sortedLuceneIds;
	private OpenBitSet docIdOpenBitSet = null; // initialize this lazily
	
	/**
	 * @param ids 
	 * @param storage 
	 * @throws IOException 
	 * 
	 */
	public StoredToLuceneDocumentsMapper(Storage storage, List<String> documentIds) throws IOException {
		luceneIds = new HashMap<Integer, Integer>(documentIds.size());
		corpusIds = new HashMap<String, Integer>(documentIds.size());
		sortedLuceneIds = new int[documentIds.size()];
		for (int i=0, len=documentIds.size(); i<len; i++) {
			int luceneDocId = storage.getLuceneManager().getLuceneDocumentId(documentIds.get(i));
			sortedLuceneIds[i] = luceneDocId;
			luceneIds.put(luceneDocId, i);
			corpusIds.put(documentIds.get(i), luceneDocId);
		}
		Arrays.sort(sortedLuceneIds);
	}
	
	public DocIdSetIterator getDocIdSetIterator() {
		return new OpenBitSetIterator(docIdOpenBitSet);
	}
	
	public OpenBitSet getDocIdOpenBitSet() {
		if (docIdOpenBitSet==null) {
			OpenBitSet obs = new OpenBitSet(sortedLuceneIds.length);
			for (int i : sortedLuceneIds) {
				obs.set((long) i);
			}
			docIdOpenBitSet = obs;
		}
		return docIdOpenBitSet;
	}


}
