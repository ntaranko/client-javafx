package com.taranko.ticketoffice.client.requesttickets;

import com.taranko.ticketoffice.client.utils.Stations;

import java.util.Date;

public class TicketRequest {
    private Stations requestDispatchStation;
    private String requestArrivalStation;
    private Date requestDispatchDate;
    private Date requestDispatchTime;
    private int requestNumberTickets;

    public Stations getRequestDispatchStation() {
        return requestDispatchStation;
    }

    public void setRequestDispatchStation(Stations requestDispatchStation) {
        this.requestDispatchStation = requestDispatchStation;
    }

    public String getRequestArrivalStation() {
        return requestArrivalStation;
    }

    public void setRequestArrivalStation(String requestArrivalStation) {
        this.requestArrivalStation = requestArrivalStation;
    }

    public Date getRequestDispatchDate() {
        return requestDispatchDate;
    }

    public void setRequestDispatchDate(Date requestDispatchDate) {
        this.requestDispatchDate = requestDispatchDate;
    }

    public Date getRequestDispatchTime() {
        return requestDispatchTime;
    }

    public void setRequestDispatchTime(Date requestDispatchTime) {
        this.requestDispatchTime = requestDispatchTime;
    }

    public int getRequestNumberTickets() {
        return requestNumberTickets;
    }

    public void setRequestNumberTickets(int requestNumberTickets) {
        this.requestNumberTickets = requestNumberTickets;
    }
}
