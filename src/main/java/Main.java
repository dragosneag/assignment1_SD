import controller.ClientController;
import controller.DestinationController;
import controller.VacantionController;
import model.Client;
import model.Destination;
import model.Vacantion;
import view.MainView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        MainView view = new MainView();
        view.initialize();
    }
}
