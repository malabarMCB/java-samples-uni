package crossplatform.fx.controller;

import crossplatform.domainLogic.IBookRepository;
import crossplatform.domainLogic.book.IdentifiedBook;
import crossplatform.fx.FxTextExtension;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddBookController {
    private IBookRepository bookRepository;

    @FXML private Text dialogText;

    //book fields
    @FXML
    private TextField udc;
    @FXML private TextField author;
    @FXML private TextField name;
    @FXML private TextField publishYear;
    @FXML private TextField instanceCount;

    public void inject(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @FXML
    private void add(ActionEvent actionEvent) {
        try {
            IdentifiedBook book= new IdentifiedBook();

            book.setUdc(udc.getText());
            book.setAuthor(author.getText());
            book.setName(name.getText());
            book.setPublishYear(Short.parseShort(publishYear.getText()));
            book.setInstanceCount(Integer.parseInt(instanceCount.getText()));

            bookRepository.add(book);
            FxTextExtension.appendText(dialogText,"Book has been added");
        } catch (Exception e) {
            FxTextExtension.appendText(dialogText,"Unable to add book:  "+e.getMessage());
        }
    }
}
