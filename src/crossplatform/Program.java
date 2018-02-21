package crossplatform;

import crossplatform.BookParser.BookParserLoggable;
import crossplatform.Books.Book;
import crossplatform.Books.IdentifiedBook;
import crossplatform.Loggers.ConsoleLogger;
import crossplatform.Loggers.FileLogger;
import crossplatform.Loggers.ILog;

import java.util.*;

public class Program {
    public static void main(String[] args) {

        System.out.println("Enter csv path");
        Scanner in=new Scanner(System.in);
        String csvFilePath= in.nextLine();

        //files
        String logFilePath=csvFilePath.replace(".csv",".log");
        String jsonFilePath=csvFilePath.replace(".csv",".json");

        //dependencies
        ILog consoleLogger= new ConsoleLogger();
        ILog fileLogger= new FileLogger(logFilePath);
        List<ILog> loggers= Arrays.asList(consoleLogger,fileLogger);
        BookParserLoggable bookParser= new BookParserLoggable(loggers);

        //program flow

        List<String> booksStr= FileUtil.readAllLines(csvFilePath,1);
        List<Book> books=new ArrayList<>();
        for (String bookStr : booksStr) {
            Book book=bookParser.Parse(bookStr);
            if(book!=null)
                books.add(book);
        }

        System.out.println("Collection:");
        for (Book book : books) {
            System.out.println(book);
        }


        List<IdentifiedBook> identifiedBooks= new ArrayList<>();
        books.forEach(b->{
            IdentifiedBook identifiedBook= new IdentifiedBook();
            identifiedBook.udc=b.udc;
            identifiedBook.author=b.author;
            identifiedBook.name=b.name;
            identifiedBook.publishYear=b.publishYear;
            identifiedBook.instanceCount=b.instanceCount;

            identifiedBooks.add(identifiedBook);
        });

        identifiedBooks.sort(Comparator.naturalOrder());

        String json=JsonUtil.convertToJson(identifiedBooks);
        FileUtil.writeLine(json,jsonFilePath);
    }
}

