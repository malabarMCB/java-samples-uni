package crossplatform.Books;

public class Book{
    public String udc;
    public String author;
    public String name;
    public Short publishYear;
    public int instanceCount;

    @Override
    public String toString() {
        return String.format("UDC: %s; author is %s; book name is %s; was publilshed in %d; number of instance: %d"
                ,udc,author,name,publishYear,instanceCount);
    }
}
