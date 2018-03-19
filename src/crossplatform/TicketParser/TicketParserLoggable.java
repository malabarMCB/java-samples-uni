package crossplatform.TicketParser;

import crossplatform.Tickets.Ticket;
import crossplatform.Loggers.ILog;

import java.util.List;

public class TicketParserLoggable{
    private List<ILog> _loggers;

    public TicketParserLoggable(List<ILog> loggers) {
        _loggers = loggers;
    }

    /**
     * parses csv string to Ticket, logs exceptions
     * @param Ticket Ticket in scv string
     * @return Ticket instance
     */
    public Ticket Parse(String Ticket){
        Ticket result=null;
        try{
            result= TicketParser.parse(Ticket);
        }
        catch(Exception e){
            for (ILog logger: _loggers) {
                logger.logError(e.getMessage());
            }
        }

        return result;
    }
}
