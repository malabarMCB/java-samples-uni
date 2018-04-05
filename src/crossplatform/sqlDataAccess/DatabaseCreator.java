package crossplatform.sqlDataAccess;

import java.sql.PreparedStatement;

public class DatabaseCreator {
    private String connectionString;

    public DatabaseCreator(String connectionString) {
        this.connectionString = connectionString;
    }

    public void recreateDatabase(){
        String query="{call RecreateTables}";

        SqlQueryExecutor.execute(connectionString,connnection -> {
            PreparedStatement statement=connnection.prepareStatement(query);
            statement.execute();
        });
    }
}
