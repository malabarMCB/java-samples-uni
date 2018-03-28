package crossplatform.domainLogic;

import crossplatform.domainLogic.book.Book;
import crossplatform.domainLogic.book.IdentifiedBook;
import crossplatform.domainLogic.bookParser.BookParserLoggable;
import crossplatform.utils.FileUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookCSVReader {
    private BookParserLoggable bookParser;

    public BookCSVReader(BookParserLoggable bookParser) {
        this.bookParser = bookParser;
    }

    public  List<IdentifiedBook> readBooks(String csvFilePath){
        List<String> booksStr = FileUtil.readAllLines(csvFilePath, 1);
        List<Book> baseBooks = new ArrayList<>();
        for (String bookStr : booksStr) {
            Book book = bookParser.Parse(bookStr);
            if (book != null)
                baseBooks.add(book);
        }

        ArrayList<IdentifiedBook> books=new ArrayList<>();
        baseBooks.forEach(b->{
            IdentifiedBook identifiedBook= new IdentifiedBook(b);
            books.add(identifiedBook);
        });

        books.sort(Comparator.naturalOrder());

        return books;
    }
}
