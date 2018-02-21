package crossplatform.Books;

public class IdentifiedBook extends Book implements Comparable<IdentifiedBook>{
    private static int _counter;
    public int id;

    public IdentifiedBook() {
        _counter++;
        id= _counter;
    }

    @Override
    public int compareTo(IdentifiedBook identifiedBook) {
        return instanceCount-identifiedBook.instanceCount;
    }
}
