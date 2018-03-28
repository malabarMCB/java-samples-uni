package crossplatform.sqlDataAccess;

import crossplatform.sqlDataAccess.functionalInterfaces.ExecuteQueryFunctionalInterface;

import java.sql.*;

public class SqlQueryExecutor {

    public static void execute(String connectionString,ExecuteQueryFunctionalInterface callback)  {
        Connection connection=getConnection(connectionString);
        if(connection==null)
            return;

        Statement statement=null;

        try {
            callback.run(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(statement!=null)
                    statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection getConnection(String connectionString){
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection= DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
