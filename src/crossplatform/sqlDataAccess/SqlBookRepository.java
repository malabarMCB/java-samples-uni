package crossplatform.sqlDataAccess;

import crossplatform.Books.IdentifiedBook;
import crossplatform.IBookRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SqlBookRepository implements IBookRepository {
    private String connectionString;

    public SqlBookRepository(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public void addRange(List<IdentifiedBook> books) {
        for (IdentifiedBook book:
             books) {
            add(book);
        }
    }

    @Override
    public void updateBook(IdentifiedBook book) {
        String query="update Book set UDC=?, Author=?, Name=?, PublishYear=?, InstanceCount=? where Id=?";

        SqlQueryExecutor.execute(connectionString,connection->{
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,book.getUdc());
            statement.setString(2,book.getAuthor());
            statement.setString(3,book.getName());
            statement.setInt(4,book.getPublishYear());
            statement.setInt(5,book.getInstanceCount());
            statement.setInt(6,book.getId());

            statement.execute();
        });
    }

    @Override
    public void deleteBook(int id) {
        String query="delete from Book where Id= ?";

        SqlQueryExecutor.execute(connectionString,connection->{
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.execute();
        });
    }

    @Override
    public IdentifiedBook getBook(int index) {
        List<IdentifiedBook> books=getAllBooks();
        return  books.get(index);
    }

    @Override
    public int getBooksCount() {
        String query="select count(*) from Book";

        AtomicInteger booksCount= new AtomicInteger();
         SqlQueryExecutor.execute(connectionString,connection->{
             Statement statement=connection.createStatement();
             ResultSet resultSet=statement.executeQuery(query);
             if(resultSet.next())
                 booksCount.set(resultSet.getInt(1));
         });

         return  booksCount.get();
    }

    @Override
    public List<IdentifiedBook> getAllBooks() {
        ArrayList<IdentifiedBook> books=new ArrayList<>();

        String query="select * from Book order by Id";

        SqlQueryExecutor.execute(connectionString,connection -> {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            if(resultSet==null)
            {
                return;
            }
            while(resultSet.next()){
                int id=resultSet.getInt("Id");
                String udc=resultSet.getString("UDC");
                String author=resultSet.getString("Author");
                String name=resultSet.getString("Name");
                Short publishYear=resultSet.getShort("PublishYear");
                int instanceCount=resultSet.getInt("InstanceCount");

                IdentifiedBook book=new IdentifiedBook(id);
                book.setUdc(udc);
                book.setAuthor(author);
                book.setName(name);
                book.setPublishYear(publishYear);
                book.setInstanceCount(instanceCount);

                books.add(book);
            }
        });

        return books;
    }

    @Override
    public void add(IdentifiedBook book) {
        String query="insert into Book values(?,?,?,?,?)";

        SqlQueryExecutor.execute(connectionString,connection -> {
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,book.getUdc());
            statement.setString(2,book.getAuthor());
            statement.setString(3,book.getName());
            statement.setShort(4,book.getPublishYear());
            statement.setInt(5,book.getInstanceCount());

            statement.execute();
        });
    }
}
