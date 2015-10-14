package net.dzikoysk.funnyguilds.data;

import net.dzikoysk.funnyguilds.basic.Rank;
import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.basic.util.BasicType;

public class SQLDatabase implements Database {

    private String prefix = "fg_";

    @Override public void loadData() {
        // ...
    }

    @Override public DataSource createRankDataSource(Rank rank) {
        if(rank.getType() == BasicType.USER) {
            return new SQLDatasource(this, prefix + "", "uuid", ((User) rank.getBasic()).getUUID().toString());
        }
        return null; // TODO
    }
}
