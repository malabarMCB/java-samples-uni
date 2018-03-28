package crossplatform.sqlDataAccess;

import crossplatform.domainLogic.journal.IJournal;
import crossplatform.domainLogic.journal.JournalEntry;
import crossplatform.domainLogic.logger.ILog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlJournal implements ILog, IJournal {
    private String connectionString;

    public SqlJournal(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public void logError(String text) {
        String query="insert into Logger (Message) values (?)";

        SqlQueryExecutor.execute(connectionString,connection->{
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,text);
            statement.execute();
        });
    }

    @Override
    public List<JournalEntry> getAllEntries() {
        String query="select * from Logger order by LogTime desc";

        List<JournalEntry> journalEntries= new ArrayList<>();
        SqlQueryExecutor.execute(connectionString,connnection -> {
            Statement statement=connnection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);

            if(resultSet==null)
                return;

            while (resultSet.next()){
                JournalEntry journalEntry= new JournalEntry();
                journalEntry.setId(resultSet.getInt("Id"));
                journalEntry.setMessage(resultSet.getString("Message"));
                journalEntry.setDate(resultSet.getDate("LogTime"));

                journalEntries.add(journalEntry);
            }
        });

        return journalEntries;
    }
}
