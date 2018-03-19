package crossplatform.fx;
        import javafx.scene.text.Text;

public class FxTextExtension {

    public static void appendText(Text textField, String text){
        String prevText=textField.getText();
        textField.setText(prevText+"\n"+text);
    }
}
