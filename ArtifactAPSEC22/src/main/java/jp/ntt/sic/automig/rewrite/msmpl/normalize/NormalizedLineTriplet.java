package jp.ntt.sic.automig.rewrite.msmpl.normalize;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalizedLineTriplet {
    public String type;
    public String verName;
    public List<String> dependedVariables;

    /**
     * constructor for metasmpl code
     *
     * @param code
     */
    public NormalizedLineTriplet(String code) {
        dependedVariables = new ArrayList<>();
        String[] line_lr = code.split("=");
        if (line_lr.length > 0) {
            String line_l = line_lr[0];
            String[] seplated_line_l = line_l.split(" ");
            if (seplated_line_l.length > 0) {
                this.verName = seplated_line_l[seplated_line_l.length - 1];
                this.type = seplated_line_l[seplated_line_l.length - 2];
            }
            Pattern p = Pattern.compile(FreshVariableGenerator.specialVariable + "[0-9]+");
            Matcher m = p.matcher(line_lr[1]);
            while (m.find()) {
                dependedVariables.add(m.group());
            }
            
            p = Pattern.compile("\\s*[a-zA-Z_]\\w*;\\s*");
            if (dependedVariables.isEmpty() && p.matcher(line_lr[1]).matches()) {
                dependedVariables.add(line_lr[1].trim().replace(";", ""));
            }
        }
    }

}
