package crossplatform.sqlDataAccess;

import java.sql.Statement;

public class DatabaseCreator {
    private String connectionString;

    public DatabaseCreator(String connectionString) {
        this.connectionString = connectionString;
    }

    public void recreateDatabase(){
        String query="IF OBJECT_ID('Book', 'U') IS NOT NULL\n" +
                "DROP TABLE Book;\n" +
                "IF OBJECT_ID('Logger', 'U') IS NOT NULL\n" +
                "DROP TABLE Logger;\n" +
                "\n" +
                "create table Book\n" +
                "(\n" +
                "\tId int primary key,\n" +
                "\tUDC nvarchar(255) not null unique,\n" +
                "\tAuthor nvarchar(255) not null,\n" +
                "\tName nvarchar(255) not null,\n" +
                "\tPublishYear int,\n" +
                "\tInstanceCount int default 0 \n" +
                ")\n" +
                "\n" +
                "create table Logger\n" +
                "(\n" +
                "\tId int primary key identity(1,1),\n" +
                "\tMessage nvarchar(255) not null,\n" +
                "\tLogTime DateTime default getdate()\n" +
                ")";

        SqlQueryExecutor.execute(connectionString,connnection -> {
            Statement statement=connnection.createStatement();
            statement.executeQuery(query);
        });
    }
}
