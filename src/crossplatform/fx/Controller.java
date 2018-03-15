package crossplatform.fx;

import crossplatform.Books.IdentifiedBook;
import crossplatform.IdentifiedBookRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {
    private FilesConfigurator filesConfigurator;
    private IdentifiedBookRepository bookRepository;

    private int currentBookNumber=0;
    private  IdentifiedBook currentBook;

    @FXML private Text logText;
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

    public void inject(FilesConfigurator fileConfigurator, IdentifiedBookRepository bookRepository) {
        this.filesConfigurator = fileConfigurator;
        this.bookRepository = bookRepository;
    }

    @FXML
    private void go(ActionEvent actionEvent){
        String csvFilePath=filesConfigurator.getCsvFilePath();
        bookRepository.readFromCsvFile(csvFilePath);
        getBook();
        goBtn.setDisable(true);
        if(bookRepository.getBooksCount()>0)
            modifyBtn.setDisable(false);
    }

    @FXML
    private void modify(ActionEvent actionEvent) {
        try {
            currentBook.setUdc(udc.getText());
            currentBook.setAuthor(author.getText());
            currentBook.setName(name.getText());
            currentBook.setPublishYear(Short.parseShort(publishYear.getText()));
            currentBook.setInstanceCount(Integer.parseInt(instanceCount.getText()));
        } catch (Exception e) {
            logText.setText("Unable to modify book! "+e.getMessage());
            logText.app
        }

        bookRepository.updateBook(currentBook);
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
    }

    @FXML
    private void chooseJsonFile(ActionEvent actionEvent) {
        filesConfigurator.chooseJsonFile();
        checkFileBtns();
    }

    @FXML
    private void chooseLogFile(ActionEvent actionEvent) {
        filesConfigurator.chooseLogFile();
        checkFileBtns();
    }

    @FXML
    private void writeToJsonFile(){
        String jsonFilePath=filesConfigurator.getJsonFilePath();
        bookRepository.writeToFileAsJsonString(jsonFilePath);
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
        chooseJsonBtn.setDisable(areFilesConfigurated);
        chooseLogBtn.setDisable(areFilesConfigurated);
    }
}
