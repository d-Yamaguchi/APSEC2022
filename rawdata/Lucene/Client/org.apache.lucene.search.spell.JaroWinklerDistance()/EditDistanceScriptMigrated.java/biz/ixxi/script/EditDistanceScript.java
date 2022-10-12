package biz.ixxi.script;
import org.elasticsearch.ElasticSearchIllegalArgumentException;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.xcontent.support.XContentMapValues;
import org.elasticsearch.index.fielddata.ScriptDocValues;
import org.elasticsearch.script.AbstractFloatSearchScript;
import org.elasticsearch.script.ExecutableScript;
import org.elasticsearch.script.NativeScriptFactory;
public class EditDistanceScript extends org.elasticsearch.script.AbstractFloatSearchScript {
    public static class Factory implements org.elasticsearch.script.NativeScriptFactory {
        @java.lang.Override
        public org.elasticsearch.script.ExecutableScript newScript(@org.elasticsearch.common.Nullable
        java.util.Map<java.lang.String, java.lang.Object> params) {
            java.lang.String fieldName = (params == null) ? null : org.elasticsearch.common.xcontent.support.XContentMapValues.nodeStringValue(params.get("field"), null);
            java.lang.String searchString = (params == null) ? "" : org.elasticsearch.common.xcontent.support.XContentMapValues.nodeStringValue(params.get("search"), "");
            java.lang.String algo = (params == null) ? "" : org.elasticsearch.common.xcontent.support.XContentMapValues.nodeStringValue(params.get("editdistance"), "ngram");
            if (fieldName == null) {
                throw new org.elasticsearch.ElasticSearchIllegalArgumentException("Missing the field parameter");
            }
            return new biz.ixxi.script.EditDistanceScript(fieldName, searchString, algo);
        }
    }

    private final java.lang.String fieldName;

    private final java.lang.String searchString;

    private java.lang.Integer previousEndIndex;

    private java.lang.String algo;

    // ESLogger logger;
    public EditDistanceScript(java.lang.String fieldName, java.lang.String searchString, java.lang.String algo) {
        this.fieldName = fieldName;
        this.searchString = searchString;
        this.algo = algo;
    }

    @java.lang.Override
    public float runAsFloat() {
        // logger.info("************** runAsFloat ****************");
        // logger = Loggers.getLogger(EditDistanceScript.class);
        // logger.info(doc().toString());
        // logger.info(name.getValues().toString());
        // String candidate = (String)source().get(fieldName);
        org.elasticsearch.index.fielddata.ScriptDocValues.Strings name = ((org.elasticsearch.index.fielddata.ScriptDocValues.Strings) (doc().get(fieldName)));
        java.lang.String candidate = name.getValues().get(0);
        // logger.info(candidate);
        if ((candidate == null) || (searchString == null)) {
            return 0.0F;
        }
        // logger.info("finalScore before for " + candidate + " and " + searchString + " => " + finalScore);
        java.lang.Float finalScore = getDistance(searchString, candidate);
        finalScore = finalScore + (score() / 100);
        // logger.info(searchString + " | " + candidate + " | " + score() + " / " + finalScore.toString());
        return finalScore;
    }

    private float getDistance(java.lang.String target, java.lang.String other) {
        org.apache.lucene.search.spell.StringDistance builder;
        if ("levenstein".equals(algo)) {
            builder = ((org.apache.lucene.search.spell.LevensteinDistance) (new org.apache.lucene.search.spell.LevensteinDistance()));
        } else if ("ngram3".equals(algo)) {
            builder = ((org.apache.lucene.search.spell.NGramDistance) (new org.apache.lucene.search.spell.NGramDistance(3)));
        } else if ("jarowinkler".equals(algo)) {
            builder = ((org.apache.lucene.search.spell.JaroWinklerDistance) (new org.apache.lucene.search.spell.JaroWinklerDistance()));
        } else if ("lucene".equals(algo)) {
            builder = ((org.apache.lucene.search.spell.LuceneLevenshteinDistance) (new org.apache.lucene.search.spell.LuceneLevenshteinDistance()));
        } else {
            builder = ((org.apache.lucene.search.spell.NGramDistance) (new org.apache.lucene.search.spell.NGramDistance()));// default size: 2

        }
        // logger.info("Algo " + builder.toString() + " " + target + " / " + other + " => " + builder.getDistance(target, other));
        return builder.getDistance(target, other);
    }
}