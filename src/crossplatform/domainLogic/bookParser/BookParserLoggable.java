package crossplatform.domainLogic.bookParser;

import crossplatform.domainLogic.books.Book;
import crossplatform.domainLogic.loggers.ILog;

import java.util.List;

public class BookParserLoggable{
    private List<ILog> _loggers;

    public BookParserLoggable(List<ILog> loggers) {
        _loggers = loggers;
    }

    /**
     * parses csv string to Book, logs exceptions
     * @param book book in scv string
     * @return Book instance
     */
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
