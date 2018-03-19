package crossplatform.Tickets;

public class IdentifiedTicket extends Ticket implements Comparable<IdentifiedTicket>{
    private static int _counter;
    private int id;

    public IdentifiedTicket() {
        _counter++;
        id= _counter;
    }

    public IdentifiedTicket(Ticket Ticket){
        this();
        setValues(Ticket);
    }

    public int getId() {
        return id;
    }

    public void setValues(Ticket Ticket){
        numberOfTicket=Ticket.numberOfTicket;
        startPoint=Ticket.startPoint;
        destination=Ticket.destination;
        firstName=Ticket.firstName;
        lastName=Ticket.lastName;
        dateApply=Ticket.dateApply;
    }

    @Override
    public int compareTo(IdentifiedTicket identifiedTicket) {
        return this.getDateApply().compareTo(identifiedTicket.getDateApply());
    }
}
