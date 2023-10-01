package edu.school21.app;

import edu.school21.reflection.FindClasses;
import edu.school21.reflection.MyReflection;

import java.util.*;

public class Program {
    private static final String PACKAGE_NAME = "edu.school21.models";
    private static MyReflection reflection;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Set<Class> listClasses = FindClasses.findAllClasses(PACKAGE_NAME);

        System.out.println("Classes:");
        for(Class foundClass : listClasses) {
            System.out.println("  - " + foundClass.getSimpleName());
        }
        System.out.println("---------------------");
        System.out.println("Enter class name:");
        String userChoice = sc.nextLine();
                boolean isFound = false;
        for(Class foundClass : listClasses) {
            if(userChoice.equals(foundClass.getSimpleName())) {
                reflection = new MyReflection(foundClass);
                isFound = true;
            }
        }
        if(!isFound) {
            System.out.println("There is no such class");
            System.exit(-1);
        }
        System.out.println("---------------------");
        System.out.println("fields:");
        Map<String, String> fields = reflection.getFields();
        fields.forEach((k, v) -> System.out.println("\t" + v + " " + k));
        System.out.println("methods:");
        Map<String, String> methods = reflection.getMethods();
        methods.forEach((k, v) -> System.out.println("\t" + v + " " + k));
        System.out.println("---------------------");
        System.out.println("Let’s create an object.");
        Map<String, String> input = new HashMap<>();
        for(String field : fields.keySet()) {
            System.out.println(field + ":");
            userChoice = sc.nextLine();
            input.put(field, userChoice);
        }
        Object myObj = reflection.createInstance(input);
        System.out.println("Object created: " + myObj);
        System.out.println("---------------------");
        System.out.println("Enter name of the field for changing:");
        userChoice = sc.nextLine();
        for(String field : fields.keySet()) {
            if(userChoice.equals(field)) {
                System.out.println("Enter " + fields.get(field) + " value:");
                userChoice = sc.nextLine();
                myObj = reflection.changeField(field, userChoice);
                System.out.println("Object updated: " + myObj);
            }
        }
        System.out.println("---------------------");
        System.out.println("Enter name of the method for call:");
        userChoice = sc.nextLine();
        List<String> paramTypes = reflection.getMethodParam(userChoice);
        List<String> inputParams = new ArrayList<>();
        for(String param : paramTypes) {
            System.out.println("Enter " + param + " value:");
            userChoice = sc.nextLine();
            inputParams.add(userChoice);
        }
        Object value = reflection.methodInvoke(inputParams, paramTypes);
        if(value != null) {
            System.out.println("Method returned:");
            System.out.println(value);
        }
    }
}