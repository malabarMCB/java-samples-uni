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
    private FilesConfigurator filesConfigurator;
    private IBookRepository bookRepository;
    private BookCSVReader bookCSVReader;
    private WindowFactory windowFactory;
    private RecreateDatabaseFunctionalInterface recreateDatabase;

    private int currentBookNumber=0;
    private  IdentifiedBook currentBook;

    @FXML private Text dialogText;
    @FXML private Button modifyBtn;

    //book fields
    @FXML private TextField udc;
    @FXML private TextField author;
    @FXML private TextField name;
    @FXML private TextField publishYear;
    @FXML private TextField instanceCount;

    //btns for files
    @FXML private Button chooseCsvBtn;
    @FXML private Button chooseJsonBtn;
    @FXML private Button chooseLogBtn;
    @FXML private Button goBtn;

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
        dialogText.setText("Choose csv and log files to start");
    }

    @FXML
    private void go(ActionEvent actionEvent){
        String csvFilePath=filesConfigurator.getCsvFilePath();
        bookRepository.addRange(bookCSVReader.readBooks(csvFilePath));
        FxTextExtension.appendText(dialogText,"Data loaded!");
        getBook();
        goBtn.setDisable(true);
        if(bookRepository.getBooksCount()>0){
            modifyBtn.setDisable(false);
            chooseJsonBtn.setDisable(false);
        }
        else{
            FxTextExtension.appendText(dialogText,"Collection is empty!");
        }
    }

    @FXML
    private void modify(ActionEvent actionEvent) {
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

    @FXML
    private void chooseCsvFile(ActionEvent actionEvent) {
        filesConfigurator.chooseCsvFile();
        checkFileBtns();
        FxTextExtension.appendText(dialogText,"CSV file was choosen");
    }

    @FXML
    private void chooseLogFile(ActionEvent actionEvent) {
        filesConfigurator.chooseLogFile();
        checkFileBtns();
        FxTextExtension.appendText(dialogText,"Log file was choosen");
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

    private void getBook(){
        checkNavigationBtns();
        currentBook=bookRepository.getBook(currentBookNumber);
        setCurrentBookFileds();
    }

    private void setCurrentBookFileds(){
        udc.setText(currentBook.getUdc());
        author.setText(currentBook.getAuthor());
        name.setText(currentBook.getName());
        publishYear.setText(currentBook.getPublishYear()==null?"":currentBook.getPublishYear().toString());
        instanceCount.setText(Integer.toString(currentBook.getInstanceCount()));
    }

    private void checkNavigationBtns(){
        prevItemBtn.setDisable(currentBookNumber==0);
        firstItemBtn.setDisable(currentBookNumber==0);
        lastItemBtn.setDisable(currentBookNumber==bookRepository.getBooksCount()-1);
        nextItemBtn.setDisable(currentBookNumber==bookRepository.getBooksCount()-1);
    }

    private void checkFileBtns(){
        boolean areFilesConfigurated=filesConfigurator.areFilesConfigurated();
        goBtn.setDisable(!areFilesConfigurated);
        chooseCsvBtn.setDisable(areFilesConfigurated);
        chooseLogBtn.setDisable(areFilesConfigurated);
    }

    public void addBook(ActionEvent actionEvent) {
        windowFactory.createAddBookWindow();
    }

    public void recreateDatabase(ActionEvent actionEvent) {
        recreateDatabase.run();
        FxTextExtension.appendText(dialogText,"Database recreated");
    }

    public void deleteBook(ActionEvent actionEvent) {
        bookRepository.deleteBook(currentBook.getId());
        FxTextExtension.appendText(dialogText,"Book deleted");
    }

    public void showJournal(ActionEvent actionEvent) {
        windowFactory.createJournalWindow();
    }
}
