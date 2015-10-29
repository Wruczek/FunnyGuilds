package net.dzikoysk.funnyguilds.ndb;

public abstract class Database implements IDatabase {

    private static Database instance;
    private final String url, username, password;

    public Database(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getConnectionURL() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public static Database getInstance() {
        if (instance == null)
            throw new UnsupportedOperationException("Database is not setup");
        return instance;
    }

    public static void setInstance(Database database) {
        if (instance != null)
            throw new UnsupportedOperationException("Database is already setup");
        instance = database;
    }
}
