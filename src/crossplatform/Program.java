package crossplatform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import crossplatform.BookParser.BookParserLoggable;
import crossplatform.Books.Book;
import crossplatform.Books.IdentifiedBook;
import crossplatform.Loggers.ConsoleLogger;
import crossplatform.Loggers.FileLogger;
import crossplatform.Loggers.ILog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Program {
    public static void main(String[] args) throws JsonProcessingException {

        //TODO uncomment when everything will be ready
/*        System.out.println("Enter csv path");
        Scanner in=new Scanner(System.in);
        String csvFilePath= in.nextLine();*/

        //TODO remove it
        String csvFilePath= "C:\\Users\\grine\\Desktop\\test.csv";

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

        //json parse
        //TODO remove to another place
        ObjectMapper mapper= new ObjectMapper();
        String json= mapper.writeValueAsString(identifiedBooks);
        FileUtil.writeLine(json,jsonFilePath);

    }
}

