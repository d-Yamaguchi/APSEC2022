package jp.ntt.sic.automig.util;

import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportUtil {

    public static String print(CtElement element) {
        if (element instanceof CtType<?>) {
            return ((CtType<?>) element).getQualifiedName();
        } else if (element instanceof CtTypeReference<?>){
            return ((CtTypeReference<?>) element).getQualifiedName();
        } else if (element instanceof CtMethod<?>) {
            String fqcn = ((CtMethod<?>) element).getDeclaringType().getQualifiedName();
            return fqcn + "." + ((CtMethod<?>) element).getSignature();
        } else if (element instanceof CtConstructor<?>) {
            String fqcn = ((CtConstructor<?>) element).getDeclaringType().getQualifiedName();
            return fqcn + "." + ((CtConstructor<?>) element).getSignature();
        } else if (element instanceof CtExecutableReference<?>) {
            String fqcn = ((CtExecutableReference<?>) element).getDeclaringType().getQualifiedName();
            return fqcn + "." + ((CtExecutableReference<?>) element).getSignature();
        } else {
            return element.prettyprint();
        }
    }


    public static void fileOut(String path, String contents) throws IOException {
        File f = new File(path);
        File dir = new File(f.getParent());
        if (!dir.exists()){
            dir.mkdirs();
        }
        FileWriter fw = new FileWriter(f);
        fw.write(contents);
        fw.close();
    }
}
