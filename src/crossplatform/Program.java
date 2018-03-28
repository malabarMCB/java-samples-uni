package crossplatform;

import crossplatform.domainLogic.BookCSVReader;
import crossplatform.domainLogic.IBookRepository;
import crossplatform.domainLogic.bookParser.BookParserLoggable;
import crossplatform.domainLogic.book.IdentifiedBook;
import crossplatform.domainLogic.logger.ConsoleLogger;
import crossplatform.domainLogic.logger.FileLogger;
import crossplatform.domainLogic.logger.ILog;
import crossplatform.fx.controller.MainController;
import crossplatform.fx.FilesConfigurator;
import crossplatform.sqlDataAccess.SqlBookRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Program extends Application {
    private final String connectionString="jdbc:sqlserver://localhost:52733;databaseName=crossplatform; integratedSecurity=true";

    @Override
    public void start(Stage stage) throws IOException {
        //database setup
        IBookRepository bookRepository=new SqlBookRepository(connectionString);

        ConsoleLogger consoleLogger= new ConsoleLogger();
        FileLogger fileLogger= new FileLogger();
        List<ILog> loggers= Arrays.asList(consoleLogger,fileLogger);
        BookParserLoggable bookParser= new BookParserLoggable(loggers);
        BookCSVReader bookCSVReader=new BookCSVReader(bookParser);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fx/view/MainView.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root, 900, 500);

        stage.setTitle("Lab 3");
        stage.setScene(scene);

        FilesConfigurator filesConfigurator= new FilesConfigurator(new FileChooser(),stage,fileLogger);

        MainController mainController =loader.getController();
        mainController.inject(filesConfigurator,bookRepository,bookCSVReader);

        stage.show();
    }

    public static void main(String[] args) throws Exception {

        String connectionString="jdbc:sqlserver://localhost:52733;databaseName=crossplatform; integratedSecurity=true";

/*
        if(!connection.isClosed()){
            System.out.println("zaebis");
        }*/
        launch(args);
/*        SqlBookRepository repository=new SqlBookRepository(connectionString);


        IdentifiedBook bookToInsert= new IdentifiedBook(2);
        bookToInsert.setUdc("sdfsdfsf");
        bookToInsert.setAuthor("auth");
        bookToInsert.setName("somename");
        Short val=5;
        bookToInsert.setPublishYear(val);
        bookToInsert.setInstanceCount(4);
        repository.add(bookToInsert);*/

        /*repository.deleteBook(2);*/

        /*repository.updateBook(bookToInsert);*/
/*        List<IdentifiedBook> books=repository.getAllBooks();
        books.forEach(System.out::println);

        System.out.println(repository.getBooksCount());*/
    }
}

