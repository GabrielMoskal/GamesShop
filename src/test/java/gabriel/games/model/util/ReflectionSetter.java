package gabriel.games.model.util;

import java.lang.reflect.Field;

public class ReflectionSetter<T> {

    private final T object;

    public ReflectionSetter(T object) {
        this.object = object;
    }

    public void setValue(final String fieldName, final Object value) {
        try {
            set(fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void set(final String fieldName, final Object value) throws Exception {
        Field field = this.object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.object, value);
    }
}