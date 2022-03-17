package repository;

import controller.ClientController;
import model.Client;
import model.Destination;
import model.Vacantion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

public class VacantionRepository {

    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("assignment1.SD");
    private static final ClientController clientController = new ClientController();

    public void insertVacantion(Vacantion vacantion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(vacantion);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Vacantion findByVacantionName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery("SELECT v FROM Vacantion v WHERE v.name = :name", Vacantion.class).
                    setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No vacantion with this name.");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public void updateVacantion(String name, Vacantion newVacantion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Vacantion vacantion = entityManager.createQuery("SELECT d FROM Vacantion d WHERE d.name = :name", Vacantion.class).
                setParameter("name", name).getSingleResult();
        vacantion.setName(newVacantion.getName());
        vacantion.setPrice(newVacantion.getPrice());
        vacantion.setStartDate(newVacantion.getStartDate());
        vacantion.setEndDate(newVacantion.getEndDate());
        vacantion.setDetails(newVacantion.getDetails());
        vacantion.setAvailable_spots(newVacantion.getAvailable_spots());
        //vacantion.setClients(newVacantion.getClients());
        entityManager.persist(vacantion);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteVacantion(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Vacantion vacantion = entityManager.createQuery("SELECT v FROM Vacantion v WHERE v.name = :name", Vacantion.class).
                setParameter("name", name).getSingleResult();
        for (Client client : vacantion.getClients()) {
            Client newClient = client;
            List<Vacantion> clientVacantions = newClient.getVacantions();
            clientVacantions.remove(vacantion);
            newClient.setVacantions(clientVacantions);
            clientController.updateClient(client.getUsername(), newClient);
        }
        entityManager.remove(vacantion);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Vacantion> getAllVacantions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery("SELECT v FROM Vacantion v", Vacantion.class).getResultList();
        } catch (NoResultException e) {
            System.out.println("No vacantions in database.");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }
}
