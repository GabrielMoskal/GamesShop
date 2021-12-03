package gabriel.games.model.util;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class ReflectionSetter<T> {

    private final T object;

    public ReflectionSetter(T object) {
        this.object = object;
    }

    public void set(final String fieldName, final Object value) {
        try {
            setField(fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setField(final String fieldName, final Object value) throws Exception {
        Field field = this.object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.object, value);
    }

    @SneakyThrows
    public Object getFieldValue(String name) {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(object);
    }
}