package crossplatform.sqlDataAccess.functionalInterface;

import java.sql.Connection;

@FunctionalInterface
public interface ExecuteQueryFunctionalInterface {
    void run( Connection connnection) throws Exception;
}
