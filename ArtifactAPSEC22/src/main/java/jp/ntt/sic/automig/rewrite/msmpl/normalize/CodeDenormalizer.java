package jp.ntt.sic.automig.rewrite.msmpl.normalize;

import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.visitor.filter.TypeFilter;

public class CodeDenormalizer {

    private static String varPrefix = "_VAR";
    
    private static String cvarPrefix = "_CVAR";
    
    public static void denormalize(CtExecutable<?> executable) {
        executable.getElements(new TypeFilter<>(CtLocalVariable.class))
            .stream()
            .filter(var -> var.getSimpleName().startsWith(varPrefix) || var.getSimpleName().startsWith(cvarPrefix))
            .peek(var -> 
                executable.getElements(new TypeFilter<>(CtVariableAccess.class))
                    .stream()
                    .filter(va -> va.getVariable().getSimpleName().equals(var.getSimpleName()))
                    .forEach(va -> va.replace(var.getDefaultExpression())))
            .forEach(var -> {
                if (var.getType().getSimpleName().equals("void")) {
                    var.replace(var.getDefaultExpression());
                } else {
                    var.delete();
                }
            });
    } 
    
}
