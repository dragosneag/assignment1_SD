package controller;

import model.Client;
import model.Destination;
import service.ClientService;

public class ClientController {

    private final ClientService clientService;

    public ClientController() {
        clientService = new ClientService();
    }

    public void createClient(String username, String password, String name) {
        Client client = new Client(username, password, name);
        clientService.createClient(client);
    }

    public Client getByName(String username) {
        return clientService.getClientByName(username);
    }

    public void updateClient(String initialUsername, Client newClient) {
        Client client = newClient;
        clientService.updateClient(initialUsername, client);
    }

    public void deleteClient(String username) {
        clientService.deleteClient(username);
    }
}
