package crossplatform;

import crossplatform.TicketParser.TicketParserLoggable;
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
        TicketParserLoggable TicketParser= new TicketParserLoggable(loggers);
        IdentifiedTicketRepository TicketRepository= new IdentifiedTicketRepository(TicketParser);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fx/View.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root, 900, 500);

        stage.setTitle("Lab 2");
        stage.setScene(scene);

        FilesConfigurator filesConfigurator= new FilesConfigurator(new FileChooser(),stage,fileLogger);

        Controller controller=loader.getController();
        controller.inject(filesConfigurator,TicketRepository);

        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}

