package service;

import model.Destination;
import model.Vacantion;
import repository.DestinationRepository;

import java.util.List;

public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService() {
        destinationRepository = new DestinationRepository();
    }

    public void createDestination(Destination destination) {
        if (destination.getName() != null && destination.getCountry() != null) {
            destinationRepository.insertDestination(destination);
        } else {
            System.out.println("Invalid destination. Could not insert in database.");
        }
    }

    public Destination getDestinationByName(String name) {
        if (name != null && !name.isEmpty()) {
            return destinationRepository.findByDestinationName(name);
        } else {
            System.out.println("Invalid name. Could not find in database.");
            return null;
        }
    }

    public void updateDestination(String name, Destination destination) {
        if (name != null && !name.isEmpty() && destination.getName() != null && destination.getCountry() != null) {
            destinationRepository.updateDestination(name, destination);
        } else {
            System.out.println("Invalid destination. Could not update in database.");
        }
    }

    public void deleteDestination(String name) {
        if (name != null && !name.isEmpty()) {
            destinationRepository.deleteDestination(name);
        } else {
            System.out.println("Invalid name. Could not delete in database.");
        }
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.getAllDestinations();
    }
}
