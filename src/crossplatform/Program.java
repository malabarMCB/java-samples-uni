package crossplatform;

import crossplatform.BookParser.BookParserLoggable;
import crossplatform.Loggers.ConsoleLogger;
import crossplatform.Loggers.FileLogger;
import crossplatform.Loggers.ILog;
import crossplatform.fx.Controller;
import crossplatform.fx.FilesConfigurator;
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

    @Override
    public void start(Stage stage) throws IOException {
        ConsoleLogger consoleLogger= new ConsoleLogger();
        FileLogger fileLogger= new FileLogger();
        List<ILog> loggers= Arrays.asList(consoleLogger,fileLogger);
        BookParserLoggable bookParser= new BookParserLoggable(loggers);
        IdentifiedBookRepository bookRepository= new IdentifiedBookRepository(bookParser);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fx/View.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root, 900, 500);

        stage.setTitle("Lab 2");
        stage.setScene(scene);

        FilesConfigurator filesConfigurator= new FilesConfigurator(new FileChooser(),stage,fileLogger);

        Controller controller=loader.getController();
        controller.inject(filesConfigurator,bookRepository);

        stage.show();
    }

    public static void main(String[] args){
        launch(args);
       /*

        //files
        String logFilePath=csvFilePath.replace(".csv",".log");
        String jsonFilePath=csvFilePath.replace(".csv",".json");

        //dependencies
        ILog consoleLogger= new ConsoleLogger();
        ILog fileLogger= new FileLogger(logFilePath);
        List<ILog> loggers= Arrays.asList(consoleLogger,fileLogger);
        BookParserLoggable bookParser= new BookParserLoggable(loggers);

        //program flow

        List<String> booksStr= FileUtil.readAllLines(csvFilePath,1);
        List<Book> books=new ArrayList<>();
        for (String bookStr : booksStr) {
            Book book=bookParser.Parse(bookStr);
            if(book!=null)
                books.add(book);
        }

        System.out.println("Collection:");
        for (Book book : books) {
            System.out.println(book);
        }


        List<IdentifiedBook> identifiedBooks= new ArrayList<>();
        books.forEach(b->{
            IdentifiedBook identifiedBook= new IdentifiedBook();
            identifiedBook.udc=b.udc;
            identifiedBook.author=b.author;
            identifiedBook.name=b.name;
            identifiedBook.publishYear=b.publishYear;
            identifiedBook.instanceCount=b.instanceCount;

            identifiedBooks.add(identifiedBook);
        });

        identifiedBooks.sort(Comparator.naturalOrder());

        String json=JsonUtil.convertToJson(identifiedBooks);
        FileUtil.writeLine(json,jsonFilePath);*/
    }
}

