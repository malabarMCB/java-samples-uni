package crossplatform;

import crossplatform.TicketParser.TicketParserLoggable;
import crossplatform.Tickets.Ticket;
import crossplatform.Tickets.IdentifiedTicket;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IdentifiedTicketRepository {
    private TicketParserLoggable TicketParser;

    private List<IdentifiedTicket> Tickets;

    public IdentifiedTicketRepository(TicketParserLoggable TicketParser) {
        this.TicketParser = TicketParser;
    }

    public int getTicketsCount(){
        return Tickets.size();
    }

    public IdentifiedTicket getTicket(int index){
        return Tickets.get(index);
    }

    public void updateTicket(IdentifiedTicket Ticket){
        for (IdentifiedTicket currentTicket : Tickets) {
            if (currentTicket.getId() == Ticket.getId()) {
                currentTicket.setValues(Ticket);
            }
        }
    }

    public void writeToFileAsJsonString(String filePath){
        String jsonString=JsonUtil.convertToJson(Tickets);
        FileUtil.writeLine(jsonString,filePath);
    }

    public void readFromCsvFile(String filePath) {
        List<String> TicketsStr = FileUtil.readAllLines(filePath, 1);
        List<Ticket> baseTickets = new ArrayList<>();
        for (String TicketStr : TicketsStr) {
            Ticket Ticket = TicketParser.Parse(TicketStr);
            if (Ticket != null)
                baseTickets.add(Ticket);
        }

        Tickets= new ArrayList<IdentifiedTicket>();
        baseTickets.forEach(b->{
            IdentifiedTicket identifiedTicket= new IdentifiedTicket(b);
            Tickets.add(identifiedTicket);
        });

        Tickets.sort(Comparator.naturalOrder());
    }
}
