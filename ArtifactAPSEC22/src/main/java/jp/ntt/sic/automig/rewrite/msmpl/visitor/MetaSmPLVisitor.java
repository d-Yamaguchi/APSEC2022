package jp.ntt.sic.automig.rewrite.msmpl.visitor;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLBody;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLConditionalAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDotMatcher;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternOr;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternRep;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDeletionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLJustMatchCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternChunk;

public interface MetaSmPLVisitor {

    void visit(SmPLBody body);

    void visit(SmPLPatternRep patternRep);

    void visit(SmPLPatternOr patternOr);

    void visit(SmPLPatternChunk patternChunk);

    void visit(SmPLDotMatcher dotMatcher);

    void visit(SmPLDeletionCode deletionCode);

    void visit(SmPLAdditionCode additionCode);

    void visit(SmPLJustMatchCode justMatchCode);

    void visit(SmPLConditionalAdditionCode conditionalAdditionCode);
    
}
