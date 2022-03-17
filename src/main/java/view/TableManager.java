package view;

import model.Destination;
import model.Vacantion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class TableManager {

    public JTable fillVacantionTable(List<Vacantion> objectList) {

        DefaultTableModel tableModel;
        String[] columns = new String[5];
        columns[0] = "Name";
        columns[1] = "Price";
        columns[2] = "Start Date";
        columns[3] = "End Date";
        columns[4] = "Destination";
        tableModel = new DefaultTableModel(columns, 0);
        for(Vacantion object : objectList){
            String[] rows = new String[5];
            rows[0] = object.getName();
            rows[1] = String.valueOf(object.getPrice());
            rows[2] = String.valueOf(object.getStartDate());
            rows[3] = String.valueOf(object.getEndDate());
            rows[4] = String.valueOf(object.getDestination().getName());
            tableModel.addRow(rows);
        }
        return new JTable(tableModel);
    }

    public JTable fillAgencyVacantionTable(List<Vacantion> objectList) {

        DefaultTableModel tableModel;
        String[] columns = new String[6];
        columns[0] = "Name";
        columns[1] = "Price";
        columns[2] = "Start Date";
        columns[3] = "End Date";
        columns[4] = "Destination";
        columns[5] = "Status";
        tableModel = new DefaultTableModel(columns, 0);
        for(Vacantion object : objectList){
            String[] rows = new String[6];
            rows[0] = object.getName();
            rows[1] = String.valueOf(object.getPrice());
            rows[2] = String.valueOf(object.getStartDate());
            rows[3] = String.valueOf(object.getEndDate());
            rows[4] = String.valueOf(object.getDestination().getName());
            rows[5] = calculateStatus(object);
            tableModel.addRow(rows);
        }
        return new JTable(tableModel);
    }

    private String calculateStatus(Vacantion vacantion) {

        Integer clientNumber = vacantion.getClients().size();
        if (clientNumber == 0) {
            return "NOT_BOOKED";
        } else {
            if (clientNumber.equals(vacantion.getAvailable_spots())) {
                return "BOOKED";
            } else {
                return "IN_PROGRESS";
            }
        }
    }

    public JTable fillDestinationTable(List<Destination> objectList) {

        DefaultTableModel tableModel;
        String[] columns = new String[3];
        columns[0] = "Name";
        columns[1] = "Country";
        columns[2] = "Details";
        tableModel = new DefaultTableModel(columns, 0);
        for(Destination object : objectList){
            String[] rows = new String[3];
            rows[0] = object.getName();
            rows[1] = object.getCountry();
            rows[2] = object.getDetails();
            tableModel.addRow(rows);
        }
        return new JTable(tableModel);
    }
}
