package net.dzikoysk.funnyguilds.data.value;

import net.dzikoysk.funnyguilds.data.DataSource;

public class Value<T> {

    protected T value;
    private String name;
    private DataSource data;

    public Value(T value, String name, DataSource data) {
        this.value = value;
        this.name = name;
        this.data = data;
    }

    public final String toString() {
        return value.toString();
    }

    public final void set(T value) {
        this.value = value;
        update();
    }

    public final T get() {
        return value;
    }

    public String getValueName() {
        return value.toString();
    }

    protected final void update(){
        data.update(name, getValueName());
    }
}
