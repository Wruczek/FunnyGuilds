package net.dzikoysk.funnyguilds.data;

import net.dzikoysk.funnyguilds.basic.Rank;

public interface Database {

    void loadData();

    DataSource createRankDataSource(Rank rank);

}
