package jp.ntt.sic.automig.rewrite.msmpl.visitor;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLConditionalAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLAdditionCode;

public class LineCounter extends MetaSmPLCodeBuilder{

    public int countCA, countA;

    public LineCounter() {
        countCA = 0;
        countA = 0;
    }

    @Override
    public void visit(SmPLConditionalAdditionCode conditionalAdditionCode) {
        countCA++;
    }

    @Override
    public void visit(SmPLAdditionCode additionCode) {
        countA++;
    }
}
