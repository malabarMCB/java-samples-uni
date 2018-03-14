package crossplatform;

import crossplatform.BookParser.BookParserLoggable;
import crossplatform.Books.Book;
import crossplatform.Books.IdentifiedBook;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        for(int i=0;i<books.size();i++){
            IdentifiedBook currentBook=books.get(i);
            if(currentBook.id== book.id){
                currentBook.udc=book.udc;
                currentBook.name=book.name;
                currentBook.author=book.author;
                currentBook.publishYear=book.publishYear;
                currentBook.instanceCount=book.instanceCount;
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
            IdentifiedBook identifiedBook= new IdentifiedBook();
            identifiedBook.udc=b.udc;
            identifiedBook.author=b.author;
            identifiedBook.name=b.name;
            identifiedBook.publishYear=b.publishYear;
            identifiedBook.instanceCount=b.instanceCount;

            books.add(identifiedBook);
        });

        books.sort(Comparator.naturalOrder());
    }
}
