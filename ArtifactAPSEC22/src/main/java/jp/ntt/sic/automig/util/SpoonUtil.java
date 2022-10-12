package jp.ntt.sic.automig.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypeInformation;
import spoon.reflect.declaration.CtTypeMember;
import spoon.reflect.path.CtPath;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtReference;
import spoon.reflect.reference.CtTypeReference;

public class SpoonUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(SpoonUtil.class);

    /**
     * Get qualified name of the type itself or the type that declares the element. 
     */
    public static String getQualifiedNameOfDeclaringType(CtElement element) {
        if (element instanceof CtTypeInformation) {
            return ((CtTypeInformation) element).getQualifiedName();
        } else if (element instanceof CtExecutableReference<?>) {
            return ((CtExecutableReference<?>) element).getDeclaringType().getQualifiedName();
        } else if (element instanceof CtFieldReference<?>) {
            return ((CtFieldReference<?>) element).getDeclaringType().getQualifiedName();
        } else if (element instanceof CtTypeMember) {
            return ((CtTypeMember) element).getDeclaringType().getQualifiedName();
        } else {
            logger.error("Unexpected API: " + element.toString());
            throw new RuntimeException();
        }
    }

    public static List<CtType<?>> findCtTypesByName(Collection<CtType<?>> types, String typeName){
        logger.debug("find " + typeName);
        boolean isQualifiedName = typeName.contains(".");
        
        // find from the model
        List<CtType<?>> result = types.stream()
                .filter(t -> isQualifiedName ? t.getQualifiedName().equals(typeName) : t.getSimpleName().equals(typeName))
                .collect(Collectors.toList());
        if (result.size() > 0) return result;
        
        // find from the standard library
        String path = isQualifiedName ? typeName.replace(".", "/") : "/" + typeName;
        try {
            return ModuleFinder.ofSystem().find("java.base").get().open().list()
                    .filter(name -> name.contains(path+".class"))
                    .map(name -> {
                        try {
                            return Class.forName(name.split("\\.")[0].replace("/", "."));
                        } catch (ClassNotFoundException e) {
                            logger.error(e.getMessage(), e);
                            throw new RuntimeException(e);
                        }
                    })
                    .map(clazz -> {
                        Launcher launcher = new Launcher();
                        launcher.getEnvironment().setNoClasspath(false);
                        return launcher.getFactory().Type().get(clazz);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static List<CtType<?>> findTypesByNameFromPath(Path rootPath, String typeName){
        try {
            String[] splitted = typeName.split("\\.");
            String simpleName = splitted[splitted.length - 1];

            List<CtType<?>> candidateTypes = Files.walk(rootPath, FileVisitOption.values())
                    .filter(f -> f.getFileName().toString().endsWith(simpleName + ".java"))
                    .map(p -> new File(p.toString()))
                    .flatMap(t -> {
                        try {
                            Launcher launcher = new Launcher();
                            launcher.addInputResource(SpoonResourceHelper.createResource(t));
                            return launcher.buildModel().getAllTypes().stream();
                        } catch (FileNotFoundException e) {
                            logger.error(e.getMessage(), e);
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
            return SpoonUtil.findCtTypesByName(candidateTypes, typeName);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    
    public static List<CtType<?>> findTypesContainingString(Path rootPath, String str){
        try {
            return Files.walk(rootPath, FileVisitOption.values())
                .filter(p -> p.getFileName().toString().endsWith(".java"))
                .filter(p -> {
                    try {
                        return Files.lines(p).anyMatch(l -> l.contains(str));
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                })
                .map(p -> new File(p.toString()))
                .flatMap(f -> {
                    try {
                        Launcher launcher = new Launcher();
                        launcher.addInputResource(SpoonResourceHelper.createResource(f));
                        return launcher.buildModel().getAllTypes().stream();
                    } catch (FileNotFoundException e) {
                        logger.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    
    public static Optional<CtElement> findElementByPathFromModel(CtModel model, CtPath path) {
        List<CtElement> els = path.evaluateOn(model.getRootPackage());
        if (els.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(els.get(0));
        }
    }
    
    public static boolean containsAPI(CtType<?> type, CtElement element) {
        if (element instanceof CtType<?>) {
            return type.getQualifiedName().equals(((CtType<?>) element).getQualifiedName());
        } else if (element instanceof CtExecutable<?>) {
            type.getDeclaredExecutables()
                .stream()
                .filter(ex -> ex.getType().equals(((CtExecutable<?>) element).getType()))
                .anyMatch(ex -> ex.getSignature().equals(((CtExecutable<?>)element).getSignature()));
        } else if (element instanceof CtField<?>) {
            return type.getFields()
                .stream()
                .filter(f -> f.getType().equals(((CtField<?>)element).getType()))
                .anyMatch(f -> f.getSimpleName().equals(((CtField<?>)element).getSimpleName()));
        }
        return false;
    }
    
    public static List<? extends CtReference> findMembersByName(CtType<?> type, String name){
        List<? extends CtReference> fields = type.getAllFields().stream()
                .filter(field -> field.getSimpleName().equals(name))
                .collect(Collectors.toList());
        if (!fields.isEmpty()) {
            return fields;
        } else {
            return type.getAllExecutables().stream()
                .filter(method -> method.getSimpleName().equals(name))
                .collect(Collectors.toList());
        }
    }
    
    public static boolean hasSameSimpleName(CtElement element1, CtElement element2) {
        String name1 = getSimpleName(element1);
        String name2 = getSimpleName(element2);
        return name1 != null && name1.equals(name2);
    }
    
    public static String getSimpleName(CtElement element) {
        return element instanceof CtType<?> ? ((CtType<?>) element).getSimpleName()
                : element instanceof CtTypeReference<?> ? ((CtTypeReference<?>) element).getSimpleName()
                : element instanceof CtTypeMember ? ((CtTypeMember) element).getSimpleName()
                : element instanceof CtReference ? ((CtReference) element).getSimpleName()
                : null;
    }
}
