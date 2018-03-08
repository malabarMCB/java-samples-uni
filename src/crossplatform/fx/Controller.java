package crossplatform.fx;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

public class Controller {
    @FXML
    private TableView<BookView> bookTableView;

    @FXML
    private TableColumn< BookView, String > udcColumn;



}
