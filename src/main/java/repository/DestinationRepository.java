package repository;

import controller.ClientController;
import controller.VacantionController;
import model.Client;
import model.Destination;
import model.Vacantion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

public class DestinationRepository {

    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("assignment1.SD");
    private static final VacantionController vacantionController = new VacantionController();

    public void insertDestination(Destination destination) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(destination);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Destination findByDestinationName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery("SELECT d FROM Destination d WHERE d.name = :name", Destination.class).
                    setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No destination with this name.");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public void updateDestination(String name, Destination newDestination) {
        Destination destination = new Destination();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        destination = entityManager.createQuery("SELECT d FROM Destination d WHERE d.name = :name", Destination.class).
                setParameter("name", name).getSingleResult();
        destination.setName(newDestination.getName());
        destination.setCountry(newDestination.getCountry());
        destination.setDetails(newDestination.getDetails());
        destination.setVacantions(newDestination.getVacantions());
        entityManager.persist(destination);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteDestination(String name) {
        Destination destination = new Destination();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        destination = entityManager.createQuery("SELECT d FROM Destination d WHERE d.name = :name", Destination.class).
                setParameter("name", name).getSingleResult();
        for (Vacantion vacantion : destination.getVacantions()) {
            vacantionController.deleteVacantion(vacantion.getName());
        }
        entityManager.remove(destination);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Destination> getAllDestinations() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery("SELECT d FROM Destination d", Destination.class).getResultList();
        } catch (NoResultException e) {
            System.out.println("No destinations in database.");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }
}
