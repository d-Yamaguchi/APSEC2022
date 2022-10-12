package jp.ntt.sic.automig.rewrite.msmpl;

import java.util.Map;

import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.declaration.CtElement;
import spoon.smpl.label.StatementLabel;
import spoon.smpl.pattern.ElemNode;
import spoon.smpl.pattern.PatternNode;

public class MyStatementLabel extends StatementLabel{

    public MyStatementLabel(CtElement codeElement) {
        super(codeElement);
        rec(this.codePattern);
    }
    
    private void rec(PatternNode node) {
        if (node instanceof ElemNode) {
            Map<String, PatternNode> sp = ((ElemNode) node).subPatterns;
            if (((ElemNode) node).elem instanceof CtInvocation<?>) {
                CtInvocation<?> inv = (CtInvocation<?>) ((ElemNode) node).elem;
                if(inv.getTarget() instanceof CtTypeAccess<?>) {
                    sp.put("target", new ElemNode(inv.getTarget()));
                }
            }
            sp.keySet()
                .forEach(k -> rec(sp.get(k)));
        }
    } 

}
