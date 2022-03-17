package controller;

import model.Client;
import model.Destination;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {

    private static final String namePattern = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*";
    private static final String datePattern = "((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])+";
    private static final String intPattern = "(\\d+)";

    public void validateClient(Client client) {

        Pattern pattern = Pattern.compile(namePattern);
        if (!pattern.matcher(client.getName()).matches()) {
            throw new IllegalArgumentException("Client name is not a valid name!");
        }
    }

    public void validateDate(String date) {

        Pattern pattern = Pattern.compile(datePattern);
        if (!pattern.matcher(date).matches()) {
            throw new IllegalArgumentException("Input date is not valid!");
        }
    }

    public void validatePrice(String price) {

        Pattern pattern = Pattern.compile(intPattern);
        if (!pattern.matcher(price).matches()) {
            throw new IllegalArgumentException("Input price is not valid!");
        }
    }

    public void validateDestination(String name, String country) {

        Pattern pattern = Pattern.compile(namePattern);
        if (!pattern.matcher(name).matches()) {
            throw new IllegalArgumentException("Name is not valid!");
        }
        if (!pattern.matcher(country).matches()) {
            throw new IllegalArgumentException("Country is not valid!");
        }
    }

    public void validateVacantion(String price, String startDate, String endDate, String availableSpots) {

        Pattern pattern2 = Pattern.compile(intPattern);
        Pattern pattern3 = Pattern.compile(datePattern);
        LocalDate stDate = LocalDate.parse(startDate);
        LocalDate enDate = LocalDate.parse(endDate);

        if (!pattern2.matcher(price).matches()) {
            throw new IllegalArgumentException("Price is not valid!");
        }
        if (!pattern3.matcher(startDate).matches()) {
            throw new IllegalArgumentException("Start date is not valid!");
        }
        if (!pattern3.matcher(endDate).matches()) {
            throw new IllegalArgumentException("End date is not valid!");
        }
        if (!pattern2.matcher(availableSpots).matches()) {
            throw new IllegalArgumentException("Available spots is not valid!");
        }
        if (stDate.isAfter(enDate)) {
            throw new IllegalArgumentException("Dates are not valid!");
        }
    }
}
