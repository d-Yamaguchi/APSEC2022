package jp.ntt.sic.automig.rewrite.msmpl.lang;

import java.util.Optional;

public class SmPLMetaVariable {
    public SmPLMetaVarTypes type;
    private String rType;
    private String id;
    private String whenConditioning;

    public SmPLMetaVariable(SmPLMetaVarTypes type) {
        this.type = type;
    }

    public String getType() {
        switch (type) {
            case Identifier : 
                return "identifier";
            case Type :
                return "type";
            case Constant : 
                return "constant";
            case Expression :
                return "expression";
            case RawType :
                return rType;
        }
        return "";
    }

    public SmPLMetaVariable setRawType(String rType) {
        this.rType = rType;
        this.type = SmPLMetaVarTypes.RawType;
        return this;
    }


    public String getId() {
        return id;
    }

    public SmPLMetaVariable setId(String id) {
        this.id = id;
        return this;
    }

    public Optional<String> getWhenConditioning() {
        return Optional.ofNullable(whenConditioning);
    }

    public SmPLMetaVariable setWhenConditioning(String pattern) {
        whenConditioning = pattern;
        return this;
    }

}
