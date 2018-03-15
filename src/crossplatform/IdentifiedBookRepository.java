package crossplatform;

import crossplatform.BookParser.BookParserLoggable;
import crossplatform.Books.Book;
import crossplatform.Books.IdentifiedBook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IdentifiedBookRepository {
    private BookParserLoggable bookParser;

    private List<IdentifiedBook> books;

    public IdentifiedBookRepository(BookParserLoggable bookParser) {
        this.bookParser = bookParser;
    }

    public int getBooksCount(){
        return books.size();
    }

    public IdentifiedBook getBook(int index){
        return books.get(index);
    }

    public void updateBook(IdentifiedBook book){
        for (IdentifiedBook currentBook : books) {
            if (currentBook.getId() == book.getId()) {
                currentBook.setValues(book);
            }
        }
    }

    public void writeToFileAsJsonString(String filePath){
        String jsonString=JsonUtil.convertToJson(books);
        FileUtil.writeLine(jsonString,filePath);
    }

    public void readFromCsvFile(String filePath) {
        List<String> booksStr = FileUtil.readAllLines(filePath, 1);
        List<Book> baseBooks = new ArrayList<>();
        for (String bookStr : booksStr) {
            Book book = bookParser.Parse(bookStr);
            if (book != null)
                baseBooks.add(book);
        }

        books= new ArrayList<IdentifiedBook>();
        baseBooks.forEach(b->{
            IdentifiedBook identifiedBook= new IdentifiedBook(b);
            books.add(identifiedBook);
        });

        books.sort(Comparator.naturalOrder());
    }
}
