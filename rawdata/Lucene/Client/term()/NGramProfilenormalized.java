/**
 * Add ngrams from a token to this profile.
 *
 * @param t
 * 		is the Token to be added
 */
public void add(org.apache.lucene.analysis.Token t) {
    java.lang.StringBuffer _CVAR0 = new java.lang.StringBuffer();
    char _CVAR1 = org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR;
    org.apache.lucene.analysis.Token _CVAR3 = t;
    java.lang.StringBuffer _CVAR2 = _CVAR0.append(_CVAR1);
    java.lang.String _CVAR4 = _CVAR3.term();
    java.lang.StringBuffer _CVAR5 = _CVAR2.append(_CVAR4);
    char _CVAR6 = org.apache.nutch.analysis.lang.custom.NGramProfile.SEPARATOR;
    java.lang.StringBuffer _CVAR7 = _CVAR5.append(_CVAR6);
    add(_CVAR7);
}