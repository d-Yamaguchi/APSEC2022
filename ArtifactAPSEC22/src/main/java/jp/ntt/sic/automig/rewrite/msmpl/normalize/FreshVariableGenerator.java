package jp.ntt.sic.automig.rewrite.msmpl.normalize;

public class FreshVariableGenerator {
    public static final String specialVariable = "_VAR";
    public static final String specialVariable_C = "_CVAR";

    private int genCnt;
    private boolean isClientMode;

    public FreshVariableGenerator(boolean clientMode){
        genCnt = 0;
        isClientMode = clientMode;
    }

    public String getFreshVar(){
        return isClientMode ? specialVariable_C+genCnt++ : specialVariable + genCnt++;
    }
}
