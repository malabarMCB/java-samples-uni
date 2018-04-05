package crossplatform.fx;

import crossplatform.domainLogic.IBookRepository;
import crossplatform.domainLogic.journal.IJournal;
import crossplatform.fx.controller.AddBookController;
import crossplatform.fx.controller.JournalController;
import crossplatform.fx.functionalInterface.ControllerInjectionFunctionalInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowFactory {
    private IJournal journal;
    private IBookRepository bookRepository;

    public WindowFactory(IJournal journal, IBookRepository bookRepository) {
        this.journal = journal;
        this.bookRepository = bookRepository;
    }

    public void createJournalWindow(){
        setUpWindow("view/JournalView.fxml","Journal view",controller -> {
            ((JournalController)controller).inject(journal);
        } );
    }

    public void createAddBookWindow(){
        setUpWindow("view/AddBookView.fxml","Book add window",controller -> {
            ((AddBookController)controller).inject(bookRepository);
        } );
    }

    private void setUpWindow(String resoursePath, String sceneTitle, ControllerInjectionFunctionalInterface injection){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resoursePath));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene= new Scene(root);
        Stage stage= new Stage();
        stage.setTitle(sceneTitle);
        stage.setScene(scene);

        injection.run(loader.getController());

        stage.show();
    }
}
