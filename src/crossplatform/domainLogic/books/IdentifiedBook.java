package crossplatform.domainLogic.books;

public class IdentifiedBook extends Book implements Comparable<IdentifiedBook>{
    private static int _counter;
    private int id;

    public IdentifiedBook() {
        _counter++;
        id= _counter;
    }

    public IdentifiedBook(int id){
        this.id=id;
    }

    public IdentifiedBook(Book book){
        this();
        setValues(book);
    }

    public int getId() {
        return id;
    }

    public void setValues(Book book){
        udc=book.udc;
        author=book.author;
        name=book.name;
        publishYear=book.publishYear;
        instanceCount=book.instanceCount;
    }

    @Override
    public int compareTo(IdentifiedBook identifiedBook) {
        return instanceCount-identifiedBook.instanceCount;
    }
}
