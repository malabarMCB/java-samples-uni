package crossplatform.BookParser;

import crossplatform.Books.Book;
import crossplatform.Loggers.ILog;

import java.util.List;

public class BookParserLoggable{
    private List<ILog> _loggers;

    public BookParserLoggable(List<ILog> loggers) {
        _loggers = loggers;
    }

    public Book Parse(String book){
        Book result=null;
        try{
            result= BookParser.parse(book);
        }
        catch(Exception e){
            for (ILog logger: _loggers) {
                logger.logError(e.getMessage());
            }
        }

        return result;
    }
}
