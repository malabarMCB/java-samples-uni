package crossplatform.sqlDataAccess.functionalInterfaces;

import java.sql.Connection;

@FunctionalInterface
public interface ExecuteQueryFunctionalInterface {
    void run( Connection connnection) throws Exception;
}
