package com.vn.demo.helper;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class LogHelper {


    private ThreadLocal<Long> id = new ThreadLocal<>();
    private final String ID_FIELD = "id";
    private ThreadLocal<Map<String, List<Object>>> differenceField = ThreadLocal.withInitial(HashMap::new);


    public Map<String, List<Object>> getDifferenceField() {
        return differenceField.get();
    }
    public Long getId() {
        return id.get();
    }

    public void clear() {
        id.remove();
        differenceField.get().clear();
        differenceField.remove();
    }

    public void compareObjects(Object oldObj, Object newObj, Long id) {
        this.id.set(id);
        differenceField.get().clear();
        Class<?> clazz1 = (oldObj != null) ? oldObj.getClass() : null;
        Class<?> clazz2 = newObj.getClass();
        try {
            Field[] fields2 = clazz2.getDeclaredFields();
            if (oldObj != null) {
                for (Field field1 : clazz1.getDeclaredFields()) {
                    field1.setAccessible(true);
                    Field field2 = findFieldByName(fields2, field1.getName());
                    if (field2 != null && !ID_FIELD.equalsIgnoreCase(field2.getName())) {
                        field2.setAccessible(true);
                        Object value1 = field1.get(oldObj);
                        Object value2 = field2.get(newObj);
                        if (value1 == null && value2 == null) {
                            continue;
                        } else if (value1 == null || !value1.equals(value2)) {
                            List<Object> values = new ArrayList<>();
                            values.add(value1);
                            values.add(value2);
                            differenceField.get().put(field1.getName(), values);
                        }
                    }
                }
            } else {
                for (Field field2 : fields2) {
                    field2.setAccessible(true);
                    Object value2 = field2.get(newObj);
                    List<Object> values = new ArrayList<>();
                    values.add(value2);
                    differenceField.get().put(field2.getName(), values);
                }
            }
        } catch (IllegalAccessException ex) {

        }
    }

    private Field findFieldByName(Field[] fields, String name) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }
}
