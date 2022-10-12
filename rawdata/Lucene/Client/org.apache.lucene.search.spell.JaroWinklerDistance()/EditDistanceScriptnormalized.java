private float getDistance(java.lang.String target, java.lang.String other) {
    org.apache.lucene.search.spell.StringDistance builder;
    if ("levenstein".equals(algo)) {
        builder = ((org.apache.lucene.search.spell.LevensteinDistance) (new org.apache.lucene.search.spell.LevensteinDistance()));
    } else if ("ngram3".equals(algo)) {
        builder = ((org.apache.lucene.search.spell.NGramDistance) (new org.apache.lucene.search.spell.NGramDistance(3)));
    } else if ("jarowinkler".equals(algo)) {
        org.apache.lucene.search.spell.JaroWinklerDistance _CVAR0 = ((org.apache.lucene.search.spell.JaroWinklerDistance) (new org.apache.lucene.search.spell.JaroWinklerDistance()));
        builder = _CVAR0;
    } else if ("lucene".equals(algo)) {
        builder = ((org.apache.lucene.search.spell.LuceneLevenshteinDistance) (new org.apache.lucene.search.spell.LuceneLevenshteinDistance()));
    } else {
        builder = ((org.apache.lucene.search.spell.NGramDistance) (new org.apache.lucene.search.spell.NGramDistance()));// default size: 2

    }
    // logger.info("Algo " + builder.toString() + " " + target + " / " + other + " => " + builder.getDistance(target, other));
    return builder.getDistance(target, other);
}