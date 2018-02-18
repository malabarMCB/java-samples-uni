package crossplatform.Books;

public class IdentifiedBook extends Book implements Comparable<IdentifiedBook>{
    public static int id;

    public IdentifiedBook() {
        id++;
    }

    @Override
    public int compareTo(IdentifiedBook identifiedBook) {
        return instanceCount-identifiedBook.instanceCount;
    }
}
