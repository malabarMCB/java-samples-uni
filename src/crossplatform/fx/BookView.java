package crossplatform.fx;

import javafx.beans.property.*;

import java.util.Optional;

//possible null- exceptions
public class BookView {
    private IntegerProperty id=new SimpleIntegerProperty(1);
    private StringProperty udc= new SimpleStringProperty("");
    private StringProperty author=new SimpleStringProperty("");
    private StringProperty name=new SimpleStringProperty();
    private ObjectProperty<Short> publishYear= new SimpleObjectProperty<Short>();
    private IntegerProperty instanceCount= new SimpleIntegerProperty(1);

    //TODO add ctor with parameters: id- required, other- optional
/*    public BookView(int id, Optional<Integer> udc){

    }*/

    public int getId(){
        return id.get();
    }

    private void setId(int id){
        this.id.set(id);
    }

    public String getUdc(){
        return udc.get();
    }

    public void setUdc(String udc){
        this.udc.set(udc);
    }

    public String getAuthor(){
        return author.get();
    }

    public void setAuthor(String author){
        this.author.set(author);
    }

    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public Short getPublishYear(){
        return publishYear.get();
    }

    public void setPublishYear(Short publishYear){
        this.publishYear.set(publishYear);
    }

    public int getInstanceCount(){
        return instanceCount.get();
    }

    public void setInstanceCount(int instanceCount){
        this.instanceCount.set(instanceCount);
    }
}
