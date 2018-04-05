package crossplatform;

import crossplatform.domainLogic.BookCSVReader;
import crossplatform.domainLogic.IBookRepository;
import crossplatform.domainLogic.bookParser.BookParserLoggable;
import crossplatform.domainLogic.logger.ConsoleLogger;
import crossplatform.domainLogic.logger.FileLogger;
import crossplatform.domainLogic.logger.ILog;
import crossplatform.fx.WindowFactory;
import crossplatform.fx.controller.MainController;
import crossplatform.fx.FilesConfigurator;
import crossplatform.sqlDataAccess.DatabaseCreator;
import crossplatform.sqlDataAccess.SqlBookRepository;
import crossplatform.sqlDataAccess.SqlJournal;
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
        //loggers
        ConsoleLogger consoleLogger= new ConsoleLogger();
        FileLogger fileLogger= new FileLogger();
        SqlJournal journal=new SqlJournal(connectionString);
        List<ILog> loggers= Arrays.asList(consoleLogger,fileLogger,journal);

        BookParserLoggable bookParser= new BookParserLoggable(loggers);
        BookCSVReader bookCSVReader=new BookCSVReader(bookParser);
        IBookRepository bookRepository=new SqlBookRepository(connectionString);
        WindowFactory windowFactory=new WindowFactory(journal,bookRepository);

        //main scene configuration
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fx/view/MainView.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Lab 3");
        stage.setScene(scene);

        FilesConfigurator filesConfigurator= new FilesConfigurator(new FileChooser(),stage,fileLogger);

        MainController mainController =loader.getController();
        DatabaseCreator databseCreator= new DatabaseCreator(connectionString);
        mainController.inject(filesConfigurator,bookRepository,bookCSVReader,windowFactory, databseCreator::recreateDatabase);

        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

