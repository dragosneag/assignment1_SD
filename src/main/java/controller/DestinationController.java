package controller;

import model.Destination;
import model.Vacantion;
import service.DestinationService;

import java.util.List;

public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController() {
        destinationService = new DestinationService();
    }

    public void createDestination(String name, String country, String details) {
        Destination destination = new Destination(name, country, details);
        destinationService.createDestination(destination);
    }

    public Destination getByName(String name) {
        return destinationService.getDestinationByName(name);
    }

    public void updateDestination(String initialName, String modifiedName, String country, String details) {
        Destination destination = new Destination(modifiedName, country, details);
        destinationService.updateDestination(initialName, destination);
    }

    public void deleteDestination(String name) {
        destinationService.deleteDestination(name);
    }

    public List<Destination> getAll() {
        return destinationService.getAllDestinations();
    }
}
