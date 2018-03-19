package crossplatform.Tickets;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class Ticket {
    protected String numberOfTicket;
    protected String startPoint;
    protected String destination;
    protected String firstName;
    protected String lastName;
    protected Date dateApply;

    final String EMPTY = "";

    @Override
    public String toString() {
        return "Ticket{" +
                "numberOfTicket='" + numberOfTicket + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateApply=" + dateApply +
                '}';
    }

    public String getNumberOfTicket() {
        return numberOfTicket;
    }

    public void setNumberOfTicket(String numberOfTicket) {
        if (!numberOfTicket.equals(EMPTY)) this.numberOfTicket = numberOfTicket;
        else throw new IllegalArgumentException();
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        if (!startPoint.equals(EMPTY)) this.startPoint = startPoint;
        else throw new IllegalArgumentException();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        if (!destination.equals(EMPTY)) this.destination = destination;
        else throw new IllegalArgumentException();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (!firstName.equals(EMPTY)) this.firstName = firstName;
        else throw new IllegalArgumentException();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (!lastName.equals(EMPTY)) this.lastName = lastName;
        else throw new IllegalArgumentException();
    }

    public Date getDateApply() {
        return dateApply;
    }

    public void setDateApply(Date dateApply) {
        this.dateApply = dateApply;
    }
}
