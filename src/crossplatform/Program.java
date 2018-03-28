package crossplatform;

import crossplatform.domainLogic.bookParser.BookParserLoggable;
import crossplatform.domainLogic.books.IdentifiedBook;
import crossplatform.domainLogic.loggers.ConsoleLogger;
import crossplatform.domainLogic.loggers.FileLogger;
import crossplatform.domainLogic.loggers.ILog;
import crossplatform.fx.controller.MainController;
import crossplatform.fx.FilesConfigurator;
import crossplatform.sqlDataAccess.SqlBookRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Program /*extends Application*/ {

    /*@Override*/
    public void start(Stage stage) throws IOException {
        ConsoleLogger consoleLogger= new ConsoleLogger();
        FileLogger fileLogger= new FileLogger();
        List<ILog> loggers= Arrays.asList(consoleLogger,fileLogger);
        BookParserLoggable bookParser= new BookParserLoggable(loggers);
        IdentifiedBookRepository bookRepository= new IdentifiedBookRepository(bookParser);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fx/view/MainView.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root, 900, 500);

        stage.setTitle("Lab 2");
        stage.setScene(scene);

        FilesConfigurator filesConfigurator= new FilesConfigurator(new FileChooser(),stage,fileLogger);

        MainController mainController =loader.getController();
        mainController.inject(filesConfigurator,bookRepository);

        stage.show();
    }

    public static void main(String[] args) throws Exception {

        String connectionString="jdbc:sqlserver://localhost:52733;databaseName=crossplatform; integratedSecurity=true";

/*        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection=DriverManager.getConnection(connectionString);

        if(!connection.isClosed()){
            System.out.println("zaebis");
        }*/
/*
        launch(args);
*/
        SqlBookRepository repository=new SqlBookRepository(connectionString);


/*        IdentifiedBook bookToInsert= new IdentifiedBook(2);
        bookToInsert.setUdc("sdfsdfsf");
        bookToInsert.setAuthor("auth");
        bookToInsert.setName("somename");
        Short val=5;
        bookToInsert.setPublishYear(val);
        bookToInsert.setInstanceCount(4);
        repository.add(bookToInsert);*/

        /*repository.deleteBook(2);*/

        /*repository.updateBook(bookToInsert);*/
        List<IdentifiedBook> books=repository.getAllBooks();
        books.forEach(System.out::println);

        System.out.println(repository.getBooksCount());
    }
}

