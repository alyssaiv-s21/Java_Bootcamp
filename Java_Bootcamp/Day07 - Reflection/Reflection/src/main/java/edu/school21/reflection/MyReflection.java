package edu.school21.reflection;

import java.lang.reflect.*;
import java.util.*;

public class MyReflection {
    private Scanner sc = new Scanner(System.in);
    private Class currentClass;
    private Field[] fields;
    private Method[] methods;

    private Method callMethod;
    private Object currentObject;

    public MyReflection(Class currentClass) {
        this.currentClass = currentClass;
    }

    public Map<String, String> getMethods() {
        HashMap<String, String> methodMap = new HashMap<>();
        Method[] methods = currentClass.getDeclaredMethods();
        this.methods = methods;
        for(int i = 0; i < methods.length; ++i) {
            String[] arrType = methods[i].getAnnotatedReturnType().toString().split("\\.");
            String methodType = arrType[arrType.length-1];
            String[] arrName = methods[i].toString().replace("java.lang.", "").split("\\.");
            String methodName = arrName[arrName.length-1];
            methodMap.put(methodName, methodType);
        }
        return methodMap;
    }

    public Map<String, String> getFields() {
        HashMap<String, String> fieldMap = new HashMap<>();
        Field[] field = currentClass.getDeclaredFields();
        this.fields = field;
        for(int i = 0; i < field.length; ++i) {
            String[] arrType = field[i].getType().toString().split("\\.");
            String fieldType = arrType[arrType.length-1];
            fieldMap.put(field[i].getName(), fieldType);
        }
        return fieldMap;
    }

    public Object createInstance(Map<String, String> input) throws Exception {
        Constructor<?> constructor = currentClass.getConstructor();
        currentObject = constructor.newInstance();
        for(Field field : fields) {
            field.setAccessible(true);
            String value = input.get(field.getName());
            if(field.getType() == String.class) {
                field.set(currentObject, value);
            } else if (field.getType() == int.class || field.getType() == Integer.class) {
                field.set(currentObject, Integer.valueOf(value));
            } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                field.set(currentObject, Boolean.valueOf(value));
            } else if (field.getType() == double.class || field.getType() == Double.class) {
                field.set(currentObject, Double.valueOf(value));
            } else if (field.getType() == long.class || field.getType() == Long.class) {
                field.set(currentObject, Long.valueOf(value));
            }
        }
        return currentObject;
    }

    public Object changeField(String fieldName, String newValue) throws IllegalAccessException {
        for(Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(currentObject, newValue);
                } else if (field.getType() == int.class || field.getType() == Integer.class) {
                    field.set(currentObject, Integer.valueOf(newValue));
                } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    field.set(currentObject, Boolean.valueOf(newValue));
                } else if (field.getType() == double.class || field.getType() == Double.class) {
                    field.set(currentObject, Double.valueOf(newValue));
                } else if (field.getType() == long.class || field.getType() == Long.class) {
                    field.set(currentObject, Long.valueOf(newValue));
                }
            }
        }
        return currentObject;
    }

    public List<String> getMethodParam(String methodName) {
        List<String> parameters = new ArrayList<>();
        for(Method method : methods) {
            String[] arrName = method.toString().replace("java.lang.", "").split("\\.");
            String shortName = arrName[arrName.length-1];
            if (shortName.equals(methodName)) {
                callMethod = method;
                Parameter[] params = method.getParameters();
                for(Parameter param : params) {
                    parameters.add(param.getType().toString().replace("class java.lang.", ""));
                }
                break;
            }
        }
        return parameters;
    }
    public Object methodInvoke(List<String> inputParams, List<String> paramTypes) throws Exception {
        Object[] obj = new Object[inputParams.size()];
        for (int i = 0; i < inputParams.size(); i++) {
            if (paramTypes.get(i).equals("String")) {
                obj[i] = inputParams.get(i);
            } else if (paramTypes.get(i).equals("int") || paramTypes.get(i).equals("Integer")) {
                obj[i] = Integer.valueOf(inputParams.get(i));
            } else if (paramTypes.get(i).equals("boolean") || paramTypes.get(i).equals("Boolean")) {
                obj[i] = Boolean.valueOf(inputParams.get(i));
            } else if (paramTypes.get(i).equals("double") || paramTypes.get(i).equals("Double")) {
                obj[i] = Double.valueOf(inputParams.get(i));
            } else if (paramTypes.get(i).equals("long") || paramTypes.get(i).equals("Long")) {
                obj[i] = Long.valueOf(inputParams.get(i));
            }
        }
        callMethod.setAccessible(true);
        Object value = callMethod.invoke(currentObject, obj);
        return value;
    }

}
