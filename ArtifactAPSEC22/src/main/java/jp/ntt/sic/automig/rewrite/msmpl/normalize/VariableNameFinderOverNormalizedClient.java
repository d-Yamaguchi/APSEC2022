package jp.ntt.sic.automig.rewrite.msmpl.normalize;

import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VariableNameFinderOverNormalizedClient{
    private final List<String> usedLocalVariables = new ArrayList<>();
    private String objective_type;
    private String replacementVarName;
    private int w;//hyper parameter
    private int d;//hyper parameter
    private CtStatement localVariable;
    private final List<CtField<?>> parentClassFields;
    private final List<CtParameter<?>> directParentExecutableParameters;


    public VariableNameFinderOverNormalizedClient(CtStatement localVariable) {
        w = 5;
        d = 2;
        this.localVariable = localVariable;
        parentClassFields = new ArrayList<>();
        directParentExecutableParameters = new ArrayList<>();
        Optional.ofNullable(this.localVariable.getParent(CtExecutable.class)).ifPresent(exe -> directParentExecutableParameters.addAll(exe.getParameters()));
        Optional.ofNullable(this.localVariable.getParent(CtClass.class)).ifPresent(c -> parentClassFields.addAll(c.getFields()));
    }

    public void setObjective_type(String objective_type) {
        this.objective_type = objective_type;
    }

    public Optional<String> find() {
        return Optional.ofNullable(replacementVarName);
    }

    public List<String> localize(List<String> usedLV){
        this.usedLocalVariables.addAll(usedLV);
        replacementVarName = null;
        List<CtLocalVariable<?>> alllocalvariables = localVariable.getParent(CtStatementList.class).getStatements().stream()
                .filter(stmt -> stmt instanceof CtLocalVariable<?>)
                .map(x -> (CtLocalVariable<?>) x)
                .collect(Collectors.toList());
        int pos = alllocalvariables.indexOf(localVariable);
        if (pos < 0) pos = 0;
        List<CtLocalVariable<?>> localvariablesInWindow = (pos < w) ? alllocalvariables.subList(0, pos) : alllocalvariables.subList(pos - w, pos);
        for (int i = localvariablesInWindow.size() - 1; i > -1; i--) {
            if (localvariablesInWindow.get(i).getType() != null && localvariablesInWindow.get(i).getType().getSimpleName().equals(objective_type) && !usedLocalVariables.contains(localvariablesInWindow.get(i).getSimpleName())) {
                replacementVarName = localvariablesInWindow.get(i).getReference().getSimpleName();
                usedLocalVariables.add(replacementVarName);
                break;
            }
        }
        if (replacementVarName == null) {
            for (CtParameter<?> directParentExecutableParameter : directParentExecutableParameters) {
                if (directParentExecutableParameter.getType() != null && directParentExecutableParameter.getType().getSimpleName().equals(objective_type) && !usedLocalVariables.contains(directParentExecutableParameter.getSimpleName())) {
                    replacementVarName = directParentExecutableParameter.getReference().getSimpleName();
                    usedLocalVariables.add(replacementVarName);
                    break;
                }
            }
        }
        if (replacementVarName == null) {
            for (CtField<?> parentClassField : parentClassFields) {
                if (parentClassField.getType() != null && parentClassField.getType().getSimpleName().equals(objective_type) && !usedLocalVariables.contains(parentClassField.getSimpleName())) {
                    replacementVarName = parentClassField.getReference().getSimpleName();
                    usedLocalVariables.add(replacementVarName);
                    break;
                }
            }
        }
        return usedLocalVariables;
    }
}
