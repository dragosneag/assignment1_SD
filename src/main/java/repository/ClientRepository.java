package repository;

import model.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.swing.*;

public class ClientRepository {

    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("assignment1.SD");

    public void insertClient(Client client) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Client findByClientUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery("SELECT c FROM Client c WHERE c.username = :username", Client.class).
                    setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Username doesn't exist!");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public void updateClient(String username, Client newClient) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.createQuery("SELECT c FROM Client c WHERE c.username = :username", Client.class).
                setParameter("username", username).getSingleResult();
        client.setUsername(newClient.getUsername());
        client.setPassword(newClient.getPassword());
        client.setName(newClient.getName());
        //client.setVacantions(newClient.getVacantions());
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteClient(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.createQuery("SELECT c FROM Client c WHERE c.username = :username", Client.class).
                setParameter("username", username).getSingleResult();
        entityManager.remove(client);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
