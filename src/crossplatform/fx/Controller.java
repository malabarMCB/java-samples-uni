package crossplatform.fx;

import crossplatform.Tickets.IdentifiedTicket;
import crossplatform.IdentifiedTicketRepository;
import crossplatform.Tickets.IdentifiedTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Controller {
    private FilesConfigurator filesConfigurator;
    private IdentifiedTicketRepository TicketRepository;

    private int currentTicketNumber=0;
    private IdentifiedTicket currentTicket;

    @FXML private Text dialogText;
    @FXML private Button modifyBtn;

    //Ticket fields
    @FXML private TextField numberOfTicket;
    @FXML private TextField startPoint;
    @FXML private TextField destination;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField dateApply;

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

    public void inject(FilesConfigurator fileConfigurator, IdentifiedTicketRepository TicketRepository) {
        this.filesConfigurator = fileConfigurator;
        this.TicketRepository = TicketRepository;
    }

    @FXML
    private void initialize(){
        dialogText.setText("Choose csv and log files to start");
    }

    @FXML
    private void go(ActionEvent actionEvent){
        String csvFilePath=filesConfigurator.getCsvFilePath();
        TicketRepository.readFromCsvFile(csvFilePath);
        FxTextExtension.appendText(dialogText,"Data loaded!");
        getTicket();
        goBtn.setDisable(true);
        if(TicketRepository.getTicketsCount()>0){
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
            currentTicket.setNumberOfTicket(numberOfTicket.getText());
            currentTicket.setStartPoint(startPoint.getText());
            currentTicket.setDestination(destination.getText());
            currentTicket.setFirstName(firstName.getText());
            currentTicket.setLastName(lastName.getText());

            TicketRepository.updateTicket(currentTicket);
            FxTextExtension.appendText(dialogText,"Ticket updated. ID: "+currentTicket.getId());
        } catch (Exception e) {
            FxTextExtension.appendText(dialogText,"Unable to modify Ticket! "+e.getMessage());
        }
    }

    @FXML
    private void getFirstItem(ActionEvent actionEvent) {
        currentTicketNumber =0;
        getTicket();
    }

    @FXML
    private void getPrevItem(ActionEvent actionEvent) {
        currentTicketNumber--;
        getTicket();
    }

    @FXML
    private void getNextItem(ActionEvent actionEvent) {
        currentTicketNumber++;
        getTicket();
    }

    @FXML
    private void getLastItem(ActionEvent actionEvent) {
        currentTicketNumber =TicketRepository.getTicketsCount()-1;
        getTicket();
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
        String jsonFilePath=filesConfigurator.getJsonFilePath();
        TicketRepository.writeToFileAsJsonString(jsonFilePath);
        FxTextExtension.appendText(dialogText,"Collection was written to json");
    }

    private void getTicket(){
        checkNavigationBtns();
        currentTicket=TicketRepository.getTicket(currentTicketNumber);
        setCurrentTicketFileds();
    }

    private void setCurrentTicketFileds(){
        numberOfTicket.setText(currentTicket.getNumberOfTicket());
        startPoint.setText(currentTicket.getStartPoint());
        destination.setText(currentTicket.getDestination());
        firstName.setText(currentTicket.getFirstName());
        lastName.setText(currentTicket.getLastName());
        dateApply.setText(currentTicket.getDateApply().toString());
    }

    private void checkNavigationBtns(){
        prevItemBtn.setDisable(currentTicketNumber==0);
        firstItemBtn.setDisable(currentTicketNumber==0);
        lastItemBtn.setDisable(currentTicketNumber==TicketRepository.getTicketsCount()-1);
        nextItemBtn.setDisable(currentTicketNumber==TicketRepository.getTicketsCount()-1);
    }

    private void checkFileBtns(){
        boolean areFilesConfigurated=filesConfigurator.areFilesConfigurated();
        goBtn.setDisable(!areFilesConfigurated);
        chooseCsvBtn.setDisable(areFilesConfigurated);
        chooseLogBtn.setDisable(areFilesConfigurated);
    }
}
