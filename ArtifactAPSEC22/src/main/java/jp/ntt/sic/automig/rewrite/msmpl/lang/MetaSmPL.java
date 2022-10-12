package jp.ntt.sic.automig.rewrite.msmpl.lang;

import java.util.List;
import java.util.Optional;

import spoon.reflect.code.CtAbstractInvocation;

public interface MetaSmPL {
    String getRuleName();

    void setRuleName(String ruleName);

    List<SmPLMetaVariable> getSmPLMetaVariables();

    void setSmPLMetaVariables(List<SmPLMetaVariable> metaVariables);

    SmPLBody getSmPLBody();

    void setSmPLBody(SmPLBody body);

    /**
     * This method creates real SmPL in DSL expression.
     *
     * @return Java DSL which is typed with CtClass and a DSL expression of SmPL
     * @param element
     */
    Optional<String> publishSmPL(CtAbstractInvocation<?> element);
}
