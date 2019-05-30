package com.taranko.ticketoffice.client.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StationList {


    public static ObservableList<String> getStationList() {

        ObservableList<String> list //
                = FXCollections.observableArrayList("Барановичи",
                "Береза",
                "Брест",
                "Витебск",
                "Гомель",
                "Гродно",
                "Жодино",
                "Кобрин",
                "Комунары",
                "Кричев",
                "Лида",
                "Лунинец",
                "Минск",
                "Могилев",
                "Молодечно",
                "Орша",
                "Осиповичи",
                "Пинск",
                "Полоцк");

        return list;
    }

}