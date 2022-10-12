package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.Benchmark;
import jp.ntt.sic.automig.rewrite.msmpl.visitor.LineCounter;
import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLCodeBuilder;
import jp.ntt.sic.automig.rewrite.msmpl.visitor.SmPLCodeLocalizer;
import jp.ntt.sic.automig.util.Pair;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;

import java.util.List;
import java.util.Optional;

public class MetaSmPLImpl implements MetaSmPL {
    private String name;
    private List<SmPLMetaVariable> metaVars;
    private SmPLBody body;


    @Override
    public String getRuleName() {
        return name;
    }

    @Override
    public void setRuleName(String ruleName) {
        name = ruleName;
    }

    @Override
    public List<SmPLMetaVariable> getSmPLMetaVariables() {
        return metaVars;
    }

    @Override
    public void setSmPLMetaVariables(List<SmPLMetaVariable> metaVariables) {
        metaVars = metaVariables;
    }

    @Override
    public SmPLBody getSmPLBody() {
        return body;
    }

    @Override
    public void setSmPLBody(SmPLBody body) {
        this.body = body;
    }

    @Override
    public Optional<String> publishSmPL(CtAbstractInvocation<?> element) {
        String sourcePosition = element.getPosition().getFile().getPath();
        CtLocalVariable<?> lv = element.getParent(CtLocalVariable.class);
        LineCounter ctMSMPL = new LineCounter();
        ctMSMPL.visit(body);
        SmPLBody clonedBody = body.clone();
        if (lv == null) {
            // element must be just an effect
            new SmPLCodeLocalizer((CtStatement) element).visit(clonedBody);// do side effect on clonedBody
            // change -/+ last 2 lines to delete variable assignment from self
            StringBuilder strb = new StringBuilder();
            strb.append("@").append(getRuleName()).append("@\n");
            getSmPLMetaVariables().forEach(mb -> {
                strb.append(mb.getType()).append(" ").append(mb.getId());
                mb.getWhenConditioning().ifPresent(cond -> strb.append(" when ").append(cond));
                strb.append(";\n");
            });
            MetaSmPLCodeBuilder visitor = new MetaSmPLCodeBuilder() {
                @Override
                public void visit(SmPLConditionalAdditionCode conditionalAdditionCode) {
                    sb.append("+ ").append(conditionalAdditionCode.getCode()).append("\n");// Remaining conditional additions are output as addition
                }
                @Override
                public void visit(SmPLDeletionCode deletionCode) {
                    String code = deletionCode.getCode();
                    code = code.split("=")[1];
                    sb.append("- ").append(code).append("\n");
                }
                @Override
                public void visit(SmPLAdditionCode additionCode) {
                    if (additionCode.isForMigrationDestination()) {
                        String code = additionCode.getCode();
                        code = code.split("=")[1];
                        sb.append("+ ").append(code).append("\n");
                    } else {
                        super.visit(additionCode);
                    }
                }
            };
            strb.append("@@\n");
            visitor.visit(clonedBody);
            strb.append(visitor.getCode());
            LineCounter ctSMPL = new LineCounter();
            ctSMPL.visit(clonedBody);
            Benchmark.conditional_insertion.add(Pair.of(sourcePosition,new int[]{ctMSMPL.countCA, ctSMPL.countA - ctMSMPL.countA}));
            return Optional.of(strb.toString());

        } else {
            new SmPLCodeLocalizer(lv).visit(clonedBody);// do side effect on clonedBody
            StringBuilder strb = new StringBuilder();
            strb.append("@").append(getRuleName()).append("@\n");
            getSmPLMetaVariables().forEach(mb -> {
                strb.append(mb.getType()).append(" ").append(mb.getId());
                mb.getWhenConditioning().ifPresent(cond -> strb.append(" when ").append(cond));
                strb.append(";\n");
            });
            MetaSmPLCodeBuilder visitor = new MetaSmPLCodeBuilder() {
                @Override
                public void visit(SmPLConditionalAdditionCode conditionalAdditionCode) {
                    sb.append("+ ").append(conditionalAdditionCode.getCode()).append("\n");
                    // Remaining conditional additions are output as addition
                }
            };
            strb.append("@@\n");
            visitor.visit(clonedBody);
            strb.append(visitor.getCode());
            LineCounter ctSMPL = new LineCounter();
            ctSMPL.visit(clonedBody);
            Benchmark.conditional_insertion.add(Pair.of(sourcePosition, new int[]{ctMSMPL.countCA, ctSMPL.countA- ctMSMPL.countA}));
            return Optional.of(strb.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("@").append(getRuleName()).append("@\n");
        getSmPLMetaVariables().forEach(mb -> {
            sb.append(mb.getType()).append(" ").append(mb.getId());
            mb.getWhenConditioning().ifPresent(cond -> sb.append(" when ").append(cond));
            sb.append(";\n");
        });
        sb.append("@@\n");
        MetaSmPLCodeBuilder visitor = new MetaSmPLCodeBuilder();
        visitor.visit(getSmPLBody());
        sb.append(visitor.getCode());
        return sb.toString();
    }

}
