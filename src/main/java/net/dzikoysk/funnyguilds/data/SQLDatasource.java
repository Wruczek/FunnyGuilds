package net.dzikoysk.funnyguilds.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SQLDatasource implements DataSource {

    private SQLDatabase sqldatabase;
    private String table;

    private String whereColumn;
    private String whereValue;

    @Override public void update(String field, String value) {
        //sqldatabase.update("UPDATE `" + table + "` SET `" + field + "`='" + value + "' WHERE `" + whereColumn + "`='" + whereValue + "';");
    }
}
