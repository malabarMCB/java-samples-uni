package crossplatform.fx.controller;

import crossplatform.domainLogic.journal.IJournal;
import crossplatform.domainLogic.journal.JournalEntry;
import crossplatform.fx.FxTextExtension;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

public class JournalController {
    private IJournal journal;

    @FXML
    private Text dialogText;

    public void inject(IJournal journal){
        this.journal=journal;

        showJournal();
    }

    private void showJournal(){
        List<JournalEntry> journalEntries=journal.getAllEntries();
        journalEntries.forEach(je->{
            FxTextExtension.appendText(dialogText,je.getMessage()+" "+je.getDate());
        });
    }
}
