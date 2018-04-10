    package crossplatform.domainLogic;

    import crossplatform.domainLogic.book.IdentifiedBook;

    import java.util.List;

    public interface IBookRepository {
        void add(IdentifiedBook book);
        void addRange(List<IdentifiedBook> books);
        void updateBook(IdentifiedBook book);
        void deleteBook(int id);
        IdentifiedBook getBook(int index);
        int getBooksCount();
        List<IdentifiedBook> getAllBooks();
    }
