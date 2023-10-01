package edu.school21.manager;

import java.sql.*;
import javax.sql.DataSource;
import java.lang.reflect.*;
import java.util.Set;

import org.reflections.Reflections;

import edu.school21.annotation.OrmEntity;
import edu.school21.annotation.OrmColumn;
import edu.school21.annotation.OrmColumnId;

public class OrmManager {
    private static final String MODELS_PACKAGE = "edu.school21.models";
    private final DataSource ds;

    public OrmManager(DataSource ds) {
        this.ds = ds;
    }
    public void initialize() {
        Set<Class<?>> classes = getAnnotatedClasses();
        for (Class<?> model : classes) {
            OrmEntity ormEntity = model.getAnnotation(OrmEntity.class);
            String tableName = ormEntity.table();
            try {
                removeTable(tableName);
                createTable(model, tableName);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void save(Object entity) {
        try {
            String query = createSaveQuery(entity);
            try(Connection conn = ds.getConnection()) {
                try(PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    System.out.println(query);
                    ps.executeUpdate();
                    ResultSet generatedKey = ps.getGeneratedKeys();
                    if(generatedKey.next()) {
                        Long id = generatedKey.getLong("id");
                        setId(entity, id);
                    }
                }
            }
        } catch (IllegalAccessException  | SQLException ex) {
            System.out.println("You can't create this entity");
        }
    }

    public void update(Object entity) {
        try {
            String query = createUpdateQuery(entity);
            try(Connection conn = ds.getConnection()) {
                try(Statement stmt = conn.createStatement()) {
                    System.out.println(query);
                    stmt.executeUpdate(query);
                }
            }
        } catch (IllegalAccessException  | SQLException ex) {
            System.out.println("You can't update this entity");
        }
    }

    public <T> T findById(Long id, Class<T> aClass) {
        try {
            T result = aClass.getConstructor().newInstance();
            String tableName = aClass.getAnnotation(OrmEntity.class).table();
            String query = "select * from " + tableName + " where id = " + id;
            try(Connection conn = ds.getConnection()) {
                try(Statement stmt = conn.createStatement()) {
                    System.out.println(query);
                    ResultSet resultSet = stmt.executeQuery(query);
                    if(resultSet.next()) {
                        objectUpdate(result, resultSet);
                        return result;
                    }
                    return null;
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException
                 | InstantiationException | IllegalAccessException
                 | SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Set<Class<?>> getAnnotatedClasses() {
        Reflections reflections = new Reflections(MODELS_PACKAGE);
        return reflections.getTypesAnnotatedWith(OrmEntity.class);
    }

    private void removeTable(String tableName) throws SQLException {
        String query = "drop table if exists " + tableName;
        try(Connection conn = ds.getConnection()) {
            try(Statement stmt = conn.createStatement()) {
                System.out.println(query);
                stmt.executeUpdate(query);
            }
        }
    }

    private void createTable(Class<?> model, String tableName) throws SQLException {
        StringBuilder createTable = new StringBuilder();
        createTable.append("create table ").append(tableName).append(" (");
        Field[] fields = model.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                createTable.append(" id bigint primary key generated always as identity, ");
            }
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                String columnName = ormColumn.name();
                String columnType = getColumnType(field.getType());
                if(columnType.equals("varchar")) {
                    int length = ormColumn.length();
                    columnType = columnType + "(" + length + ")";
                }
                createTable.append(" ").append(columnName).append(" ").append(columnType).append(", ");
            }
        }
        createTable.setLength(createTable.length() - 2);
        createTable.append(")");
        try(Connection conn = ds.getConnection()) {
            try(Statement stmt = conn.createStatement()) {
                System.out.println(createTable);
                stmt.executeUpdate(createTable.toString());
            }
        }
    }

    private String getColumnType(Class<?> fieldType) {
        if (fieldType == String.class) {
            return "varchar";
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return "int";
        } else if (fieldType == Long.class || fieldType == long.class) {
            return "bigint";
        } else if (fieldType == Double.class || fieldType == double.class) {
            return "double";
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return "boolean";
        } else {
            return "varchar";
        }
    }

    private String createSaveQuery(Object entity) throws IllegalAccessException {
        Class<?> entityClass = entity.getClass();
        String tableName = entityClass.getAnnotation(OrmEntity.class).table();
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder queryInsert = new StringBuilder();
        queryInsert.append("insert into ").append(tableName).append(" (");
        for(Field field : fields) {
            if(field.isAnnotationPresent(OrmColumn.class)) {
                String columnName = field.getAnnotation(OrmColumn.class).name();
                queryInsert.append(columnName).append(",");
            }
        }
        queryInsert.deleteCharAt(queryInsert.length()-1);
        queryInsert.append(") values (");
        for(Field field : fields) {
            if(field.isAnnotationPresent(OrmColumn.class)) {
                field.setAccessible(true);
                Object columnValue = field.get(entity);
                if(columnValue != null) {
                    queryInsert.append("'").append(columnValue).append("',");
                } else {
                    queryInsert.append("null,");
                }
            }
        }
        queryInsert.deleteCharAt(queryInsert.length()-1);
        queryInsert.append(")");
        return queryInsert.toString();
    }

    private void setId(Object entity, Long id) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(OrmColumnId.class)) {
                field.setAccessible(true);
                field.set(entity, id);
            }
        }
    }

    private String createUpdateQuery(Object entity) throws  IllegalAccessException {
        Class<?> entityClass = entity.getClass();
        String tableName = entityClass.getAnnotation(OrmEntity.class).table();
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder queryUpdate = new StringBuilder();
        queryUpdate.append("update ").append(tableName).append(" set ");
        Long id = 0L;
        for(Field field: fields) {
            if(field.isAnnotationPresent(OrmColumn.class)) {
                String columnName = field.getAnnotation(OrmColumn.class).name();
                queryUpdate.append(columnName).append(" = ");
                field.setAccessible(true);
                Object columnValue = field.get(entity);
                if(columnValue != null) {
                    queryUpdate.append("'").append(columnValue).append("',");
                } else {
                    queryUpdate.append("null,");
                }
            }
            if(field.isAnnotationPresent(OrmColumnId.class)) {
                field.setAccessible(true);
                id =  (Long) field.get(entity);
            }
        }
        queryUpdate.deleteCharAt(queryUpdate.length()-1);
        queryUpdate.append(" where id = ").append(id);
        return queryUpdate.toString();
    }

    private <T> void objectUpdate(T obj, ResultSet resultSet) throws SQLException,IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(OrmColumnId.class)) {
                field.setAccessible(true);
                field.set(obj, resultSet.getLong("id"));
            }
            if(field.isAnnotationPresent(OrmColumn.class)) {
                field.setAccessible(true);
                String columnName = field.getAnnotation(OrmColumn.class).name();
                if(field.getType() == String.class) {
                    field.set(obj, resultSet.getString(columnName));
                } else if (field.getType() == Integer.class || field.getType() == int.class) {
                    field.set(obj, resultSet.getInt(columnName));
                } else if (field.getType() == Long.class || field.getType() == long.class) {
                    field.set(obj, resultSet.getLong(columnName));
                } else if (field.getType() == Double.class || field.getType() == double.class) {
                    field.set(obj, resultSet.getDouble(columnName));
                } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                    field.set(obj, resultSet.getBoolean(columnName));
                }
            }

        }
    }
}
