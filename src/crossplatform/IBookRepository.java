package crossplatform;

import crossplatform.Books.IdentifiedBook;

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
