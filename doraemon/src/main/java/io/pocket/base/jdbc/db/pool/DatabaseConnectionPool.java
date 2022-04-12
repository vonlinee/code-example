package io.pocket.base.jdbc.db.pool;

import javax.sql.DataSource;

public abstract class DatabaseConnectionPool {

    public abstract String vendor();

    public abstract String website();

    public abstract DataSource pickDataSource();

}