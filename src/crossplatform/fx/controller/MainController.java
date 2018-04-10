package crossplatform.fx.controller;

import crossplatform.domainLogic.BookCSVReader;
import crossplatform.domainLogic.IBookRepository;
import crossplatform.domainLogic.book.IdentifiedBook;
import crossplatform.fx.FilesConfigurator;
import crossplatform.fx.FxTextExtension;
import crossplatform.fx.WindowFactory;
import crossplatform.fx.functionalInterface.RecreateDatabaseFunctionalInterface;
import crossplatform.utils.FileUtil;
import crossplatform.utils.JsonUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

public class MainController {
    //incoming dependencies
    private FilesConfigurator filesConfigurator;
    private IBookRepository bookRepository;
    private BookCSVReader bookCSVReader;
    private WindowFactory windowFactory;
    private RecreateDatabaseFunctionalInterface recreateDatabase;

    //fields for paging
    private int currentBookNumber;
    private  IdentifiedBook currentBook;

    //fxml fields

    @FXML private Text dialogText;

    @FXML private Button modifyBtn;
    @FXML private Button addBtn;
    @FXML private Button deleteBtn;

    //book fields
    @FXML private TextField udc;
    @FXML private TextField author;
    @FXML private TextField name;
    @FXML private TextField publishYear;
    @FXML private TextField instanceCount;

    //btns for files
    @FXML private Button csvLoadBtn;
    @FXML private Button chooseJsonBtn;
    @FXML private Button chooseLogBtn;
    @FXML private Button goBtn;
    @FXML private Button showJournalBtn;
    @FXML private Button recreateDatabaseBtn;

    //paging btns
    @FXML private Button firstItemBtn;
    @FXML private Button prevItemBtn;
    @FXML private Button nextItemBtn;
    @FXML private Button lastItemBtn;

    public void inject(FilesConfigurator filesConfigurator, IBookRepository bookRepository, BookCSVReader bookCSVReader, WindowFactory windowFactory, RecreateDatabaseFunctionalInterface recreateDatabase) {
        this.filesConfigurator = filesConfigurator;
        this.bookRepository = bookRepository;
        this.bookCSVReader = bookCSVReader;
        this.windowFactory = windowFactory;
        this.recreateDatabase = recreateDatabase;
    }

    @FXML
    private void initialize(){
        dialogText.setText("Choose log file to start");
    }

    @FXML
    private void go(ActionEvent actionEvent){
        currentBookNumber=0;
        getBook();
        modifyBtn.setDisable(false);
        addBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    //region paging actions
    @FXML
    private void getFirstItem(ActionEvent actionEvent) {
        currentBookNumber =0;
        getBook();
    }

    @FXML
    private void getPrevItem(ActionEvent actionEvent) {
        currentBookNumber--;
        getBook();
    }

    @FXML
    private void getNextItem(ActionEvent actionEvent) {
        currentBookNumber++;
        getBook();
    }

    @FXML
    private void getLastItem(ActionEvent actionEvent) {
        currentBookNumber =bookRepository.getBooksCount()-1;
        getBook();
    }
    //endregion

    @FXML
    private void chooseLogFile(ActionEvent actionEvent) {
        filesConfigurator.chooseLogFile();
        checkFileBtns();
        FxTextExtension.appendText(dialogText,"Log file was choosen");
    }

    @FXML
    private void loadFromCsvFile(ActionEvent actionEvent) {
        filesConfigurator.chooseCsvFile();
        String csvFilePath=filesConfigurator.getCsvFilePath();
        bookRepository.addRange(bookCSVReader.readBooks(csvFilePath));
        checkFileBtns();
        FxTextExtension.appendText(dialogText,"CSV file was choosen");
    }

    @FXML
    private void writeToJsonFile(ActionEvent actionEvent){
        filesConfigurator.chooseJsonFile();

        List<IdentifiedBook> books= bookRepository.getAllBooks();
        String jsonStr= JsonUtil.convertToJson(books);

        String jsonFilePath=filesConfigurator.getJsonFilePath();
        FileUtil.writeLine(jsonStr,jsonFilePath);

        FxTextExtension.appendText(dialogText,"Collection was written to json");
    }

    @FXML
    private void recreateDatabase(ActionEvent actionEvent) {
        recreateDatabase.run();
        currentBook=null;
        currentBookNumber=0;
        checkNavigationBtns();
        FxTextExtension.appendText(dialogText,"Database recreated");
    }

    @FXML
    private void showJournal(ActionEvent actionEvent) {
        windowFactory.createJournalWindow();
    }

    private void getBook(){
        checkNavigationBtns();
        currentBook=bookRepository.getBook(currentBookNumber);
        setCurrentBookFileds();
    }

    private void setCurrentBookFileds(){
        if(currentBook==null){
            udc.setText("");
            author.setText("");
            name.setText("");
            publishYear.setText("");
            instanceCount.setText("");
        }
        else{
            udc.setText(currentBook.getUdc());
            author.setText(currentBook.getAuthor());
            name.setText(currentBook.getName());
            publishYear.setText(currentBook.getPublishYear()==null?"":currentBook.getPublishYear().toString());
            instanceCount.setText(Integer.toString(currentBook.getInstanceCount()));
        }
    }

    private void checkNavigationBtns(){
        prevItemBtn.setDisable(currentBookNumber==0);
        firstItemBtn.setDisable(currentBookNumber==0);
        lastItemBtn.setDisable(currentBookNumber>=bookRepository.getBooksCount()-1);
        nextItemBtn.setDisable(currentBookNumber>=bookRepository.getBooksCount()-1);
    }

    private void checkFileBtns(){
        boolean areFilesConfigurated=filesConfigurator.areFilesConfigurated();

        chooseLogBtn.setDisable(areFilesConfigurated);

        csvLoadBtn.setDisable(!areFilesConfigurated);
        goBtn.setDisable(!areFilesConfigurated);
        recreateDatabaseBtn.setDisable(!areFilesConfigurated);
        showJournalBtn.setDisable(!areFilesConfigurated);
        chooseJsonBtn.setDisable(!areFilesConfigurated);
    }

    //region create-update-delete
    @FXML
    private void addBook(ActionEvent actionEvent) {
        windowFactory.createAddBookWindow();
    }

    @FXML
    private void deleteBook(ActionEvent actionEvent) {
        if(currentBook==null)
            return;
        bookRepository.deleteBook(currentBook.getId());
        currentBookNumber=currentBookNumber==0?0:currentBookNumber-1;
        getBook();
        FxTextExtension.appendText(dialogText,"Book deleted");
    }

    @FXML
    private void modify(ActionEvent actionEvent) {
        if(currentBook==null)
            return;

        try {
            currentBook.setUdc(udc.getText());
            currentBook.setAuthor(author.getText());
            currentBook.setName(name.getText());
            currentBook.setPublishYear(Short.parseShort(publishYear.getText()));
            currentBook.setInstanceCount(Integer.parseInt(instanceCount.getText()));

            bookRepository.updateBook(currentBook);
            FxTextExtension.appendText(dialogText,"Book updated. ID: "+currentBook.getId());
        } catch (Exception e) {
            FxTextExtension.appendText(dialogText,"Unable to modify book! "+e.getMessage());
        }
    }
    //endregion
}
