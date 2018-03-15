package crossplatform.Books;

public class IdentifiedBook extends Book implements Comparable<IdentifiedBook>{
    private static int _counter;
    public int id;

    public IdentifiedBook() {
        _counter++;
        id= _counter;
    }

    public IdentifiedBook(Book book){
        this();
        setValues(book);
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
