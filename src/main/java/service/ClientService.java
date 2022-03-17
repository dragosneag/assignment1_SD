package service;

import model.Client;
import model.Destination;
import repository.ClientRepository;

public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService() {
        clientRepository = new ClientRepository();
    }

    public void createClient(Client client) {
        if (client.getName() != null && client.getUsername() != null && client.getPassword() != null) {
            clientRepository.insertClient(client);
        } else {
            System.out.println("Invalid client. Could not insert in database.");
        }
    }

    public Client getClientByName(String username) {
        if (username != null && !username.isEmpty()) {
            return clientRepository.findByClientUsername(username);
        } else {
            System.out.println("Invalid username. Could not find in database.");
            return null;
        }
    }

    public void updateClient(String username, Client client) {
        if (username != null && !username.isEmpty() && client.getName() != null && client.getUsername() != null && client.getPassword() != null) {
            clientRepository.updateClient(username, client);
        } else {
            System.out.println("Invalid client. Could not update in database.");
        }
    }

    public void deleteClient(String username) {
        if (username != null && !username.isEmpty()) {
            clientRepository.deleteClient(username);
        } else {
            System.out.println("Invalid username. Could not delete in database.");
        }
    }
}
