package crossplatform.fx;

import crossplatform.domainLogic.loggers.FileLogger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FilesConfigurator {
    private FileChooser fileChooser;
    private Stage stage;
    private FileLogger fileLogger;

    private File csvFile;
    private File jsonFile;
    private File logFile;

    public FilesConfigurator(FileChooser fileChooser, Stage stage, FileLogger fileLogger) {
        this.fileChooser = fileChooser;
        this.stage = stage;
        this.fileLogger = fileLogger;
    }

    public boolean areFilesConfigurated() {
        return csvFile!=null && logFile!=null;
    }


    public void chooseCsvFile(){
        csvFile=fileChooser.showOpenDialog(stage);
    }

    public void chooseJsonFile(){
        jsonFile=fileChooser.showOpenDialog(stage);
    }

    public void chooseLogFile(){
        logFile=fileChooser.showOpenDialog(stage);
        fileLogger.setFile(logFile.getAbsolutePath());
    }

    public String getCsvFilePath() {
        return csvFile.getAbsolutePath();
    }

    public String getJsonFilePath() {
        return jsonFile.getAbsolutePath();
    }
}
