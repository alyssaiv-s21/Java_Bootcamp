package edu.school21.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;
import java.util.stream.Collectors;

public class FindClasses {
    public static Set<Class> findAllClasses(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .collect(Collectors.toSet());
    }
}
