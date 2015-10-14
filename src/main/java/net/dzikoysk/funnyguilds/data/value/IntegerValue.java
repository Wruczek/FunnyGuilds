package net.dzikoysk.funnyguilds.data.value;

import net.dzikoysk.funnyguilds.data.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerValue extends Value<Integer> {

    public IntegerValue(int v, String name, DataSource data){
        super(v, name, data);
    }

    public void add(int v){
        value = value + v;
        update();
    }

    public void subtract(int v){
        value = value - v;
        update();
    }
}
