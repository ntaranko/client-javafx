package com.taranko.ticketoffice.client.controllers;

import com.taranko.ticketoffice.client.requesttickets.TicketsRequest;
import com.taranko.ticketoffice.client.utils.DateUtils;
import com.taranko.ticketoffice.client.utils.NumbersOfTicketsList;
import com.taranko.ticketoffice.client.utils.StationList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class RequestTicketsController {

    @FXML
    private ComboBox<String> dispatchStationSelector;


    @FXML
    private ComboBox<String> arrivalStationSelector;

    @FXML
    private DatePicker dispatchDateSelector;

    @FXML
    private TextField hoursTime;

    @FXML
    private TextField minutesTime;

    @FXML
    private ComboBox<Integer> numberOfTicketsSelector;

    @FXML
    private Button searchBtn;

    @FXML
    public void initialize() {
        ObservableList stationsLlist = StationList.getStationList();
        ObservableList numberOfTicketsList = NumbersOfTicketsList.getNumberOfTicketsList();

        dispatchStationSelector.setItems(stationsLlist);
        arrivalStationSelector.setItems(stationsLlist);
        dispatchDateSelector.setValue(LocalDate.now());
        dispatchDateSelector.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        numberOfTicketsSelector.setItems(numberOfTicketsList);
        numberOfTicketsSelector.setValue(1);


        searchBtn.setOnAction(event -> {

            String dispatchStation = dispatchStationSelector.getValue();
            String arrivalStation = arrivalStationSelector.getValue();

            LocalDate dispatchDateSelected = dispatchDateSelector.getValue();
            int year = dispatchDateSelected.getYear();
            int month = dispatchDateSelected.getMonthValue();
            int day = dispatchDateSelected.getDayOfMonth();
            DateUtils du = new DateUtils();
            Date dispatchDate = du.buildDate(year, month, day);

            Integer hours = Integer.parseInt(hoursTime.getText());
            Integer minutes = Integer.parseInt(minutesTime.getText());
            String dispatchTimeSting = hours.toString() + ":" + minutes.toString();
            Date dispatchTime = null;
            try {
                dispatchTime = du.buildTimeFromString(dispatchTimeSting);
            } catch (ParseException e) {
                Alert loginWarning = new Alert(Alert.AlertType.ERROR);
                loginWarning.setHeaderText("Validation");
                loginWarning.setContentText("Wrong data entered");
                loginWarning.showAndWait().ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        if (buttonType == ButtonType.OK) {
                            loginWarning.hide();
                        }
                    }
                });
            }

            int numberOfTickets = numberOfTicketsSelector.getValue();

            JSONObject serverJson = new JSONObject();

            if (!correctTicketsRequest(dispatchStation, arrivalStation, dispatchDate, hours, minutes)) { //добавить проверки: на непустые значения, на нетекстовые значения в полях времени
                Alert loginWarning = new Alert(Alert.AlertType.ERROR);
                loginWarning.setHeaderText("Validation");
                loginWarning.setContentText("Wrong data entered");
                loginWarning.showAndWait().ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        if (buttonType == ButtonType.OK) {
                            loginWarning.hide();
                        }
                    }
                });
            } else {
                TicketsRequest ticketsRequest = new TicketsRequest();
                try {
                    serverJson = ticketsRequest.send(dispatchStation, arrivalStation, dispatchDate, dispatchTime, numberOfTickets);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JSONObject responseData = serverJson.getJSONObject("response-data");
            Integer responseCode = responseData.getInt("response-code");
            JSONObject response = serverJson.getJSONObject("response");

            if (responseCode == 405) {
                Alert loginWarning = new Alert(Alert.AlertType.ERROR);
                loginWarning.setHeaderText("Tickets Request");
                loginWarning.setContentText("No available tickets! Change search criteria and try again!");
                loginWarning.showAndWait().ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        if (buttonType == ButtonType.OK) {
                            loginWarning.hide();
                        }
                    }
                });

            } else {
                Alert loginWarning = new Alert(Alert.AlertType.INFORMATION);
                loginWarning.setContentText("Tickets are available");
                loginWarning.showAndWait().ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        if (buttonType == ButtonType.OK) {
                            loginWarning.hide();



                        }
                    }
                });

            }
          /*  System.out.println(hours + minutes);
            System.out.println(dispatchStation + arrivalStation);
            System.out.println(dispatchDate.toString());*/


        });

    }

    private boolean correctTicketsRequest(String dispatchStation, String arrivalStation, Date dispatchDate, int hours, int minutes) {
        boolean correctRequest = (hours >= 0 && hours <= 23) && (minutes >= 0 && minutes <= 59)
                && !dispatchStation.equals(arrivalStation)
                && dispatchDate.after(new Date());

        return correctRequest;
    }
}
