package com.taranko.ticketoffice.client.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NumbersOfTicketsList {


    public static ObservableList<Integer> getNumberOfTicketsList() {

        ObservableList<Integer> list //
                = FXCollections.observableArrayList(1,
                2,
                3,
                4,
                5,
                6);

        return list;
    }

}