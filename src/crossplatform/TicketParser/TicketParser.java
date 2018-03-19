package crossplatform.TicketParser;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import crossplatform.Tickets.Ticket;
import org.apache.commons.lang3.StringUtils;

import java.time.Year;

public class TicketParser{

    private static final String ERRORMESSAGE="Invalid Ticket data! ";

    /**
     * parses csv string to Ticket
     * @param TicketStr csv string
     * @return Ticket instance
     * @throws Exception exception that appears during parsing
     */
    public static Ticket parse(String TicketStr) throws Exception {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("numberOfTicket")
                .addColumn("startPoint")
                .addColumn("destination")
                .addColumn("firstName")
                .addColumn("lastName")
                .addColumn("dateApply")
                .build();
        Ticket Ticket=null;
        try{
            Ticket = mapper
                    .reader(Ticket.class)
                    .with(schema)
                    .readValue(TicketStr);
        }
        catch (Exception e){
            throw new Exception(ERRORMESSAGE+TicketStr);
        }

        if(StringUtils.isBlank(Ticket.getFirstName())
                || StringUtils.isBlank(Ticket.getLastName())
                || StringUtils.isBlank(Ticket.getDestination())
                || StringUtils.isBlank(Ticket.getNumberOfTicket())
                || StringUtils.isBlank(Ticket.getStartPoint()))
            throw new Exception(ERRORMESSAGE+TicketStr);


        return Ticket;
    }
}
