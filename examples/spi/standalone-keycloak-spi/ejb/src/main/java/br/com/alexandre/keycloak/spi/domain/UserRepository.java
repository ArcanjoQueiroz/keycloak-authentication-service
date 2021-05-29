package br.com.alexandre.keycloak.spi.domain;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.jboss.logging.Logger;

public class UserRepository {

  private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

  private final EntityManagerFactory entityManagerFactory;

  public UserRepository(final EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  public boolean validateCredentials(final String username, final String password) {
    final String encryptedPassword = encrypt(password);
    final User user = findUserByUsername(username);
    if (user == null) {
      LOGGER.warn("User with username " + username + " not found");
      return false;
    }
    LOGGER.info("User Password: " + password + ", Encrypted Password: " + encryptedPassword);
    return user.getPassword() != null && user.getPassword().equals(encryptedPassword);
  }

  private String encrypt(final String plainPassword) {
    // TODO You need to implement the algorithm
    return plainPassword;
  }

  public User updateCredentials(final String username, final String password) {
    final String encryptedPassword = encrypt(password);
    final EntityManager entityManager = createEntityManager();

    final User user = findUserByUsername(entityManager, username);
    try {
      if (user == null) {
        LOGGER.warn("User with username " + username + " not found");
        return null;
      }
      user.setPassword(encryptedPassword);
      entityManager.getTransaction().begin();
      entityManager.persist(user);
      entityManager.getTransaction().commit();
    } catch (final RuntimeException e) {
      entityManager.getTransaction().rollback();
    } finally {
      entityManager.close();
    }
    return user;
  }

  public int getUsersCount() {
    final EntityManager entityManager = createEntityManager();
    try {
      final TypedQuery<Long> query = entityManager.createNamedQuery("count", Long.class);
      return query.getSingleResult() != null ? query.getSingleResult().intValue() : 0;
    } finally {
      entityManager.close();
    }
  }

  public User findUserByUsernameOrEmail(final String usernameOrEmail) {
    final EntityManager entityManager = createEntityManager();
    try {
      return entityManager.createNamedQuery("findByUsernameOrEmail", User.class)
          .setParameter("search", usernameOrEmail != null ? usernameOrEmail.trim().toLowerCase() : "")
          .setMaxResults(1)
          .getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      LOGGER.warn("No result found for username or email " + usernameOrEmail);      
      return null;      
    } catch (javax.persistence.NonUniqueResultException e) {
      LOGGER.warn("More than one result for username or email " + usernameOrEmail);
      return null;
    } finally {
      entityManager.close();
    }
  }

  public User findUserByUsername(final String username) {
    final EntityManager entityManager = createEntityManager();
    try {
      return entityManager.createNamedQuery("findByUsername", User.class)
          .setParameter("username", username != null ? username.trim().toLowerCase() : "")
          .setMaxResults(1)
          .getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      LOGGER.warn("No result found for username " + username);      
      return null;
    } catch (javax.persistence.NonUniqueResultException e) {
      LOGGER.warn("More than one result for username " + username);
      return null;
    } finally {
      entityManager.close();
    }
  }

  private User findUserByUsername(final EntityManager entityManager, final String username) {
    try {
      return entityManager.createNamedQuery("findByUsername", User.class)
          .setParameter("username", username != null ? username.trim().toLowerCase() : "")
          .setMaxResults(1)
          .getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      LOGGER.warn("No result found for username " + username);
      return null;
    } catch (javax.persistence.NonUniqueResultException e) {
      LOGGER.warn("More than one result for username " + username);
      return null;
    }
  }

  public User findUserByEmail(final String email) {
    final EntityManager entityManager = createEntityManager();
    try {    
      return entityManager.createNamedQuery("findByEmail", User.class)
          .setParameter("email", email != null ? email.trim().toLowerCase() : "")
          .setMaxResults(1)
          .getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      return null;      
    } catch (javax.persistence.NonUniqueResultException e) {
      LOGGER.warn("More than one result for email " + email);
      return null;
    } finally {
      entityManager.close();
    }
  }

  public Collection<User> findAll() {
    final EntityManager entityManager = createEntityManager();
    try {
      return entityManager.createNamedQuery("findAll", User.class).getResultList();
    } finally {
      entityManager.close();      
    }
  }

  public Collection<User> findAll(final int firstResult, final int maxResults) {
    final EntityManager entityManager = createEntityManager();
    try {   
      return entityManager.createNamedQuery("findAll", User.class)
          .setFirstResult(firstResult)
          .setMaxResults(maxResults)
          .getResultList();
    } finally {
      entityManager.close();
    }
  }

  public Collection<User> findAllByUsernameOrEmail(final String search) {
    final EntityManager entityManager = createEntityManager();
    try {  
      return entityManager.createNamedQuery("findByUsernameOrEmail", User.class)
          .setParameter("search", search != null ? search.trim().toLowerCase() : "")
          .getResultList();
    } finally {
      entityManager.close();
    }
  }

  public Collection<User> findAllByUsernameOrEmail(
      final String search, final int firstResult, final int maxResults) {
    final EntityManager entityManager = createEntityManager();
    try {  
      return entityManager.createNamedQuery("findByUsernameOrEmail", User.class)
          .setParameter("search", search != null ? search.trim().toLowerCase() : "")
          .setFirstResult(firstResult)
          .setMaxResults(maxResults)
          .getResultList();
    } finally {
      entityManager.close();
    }
  }

  public boolean remove(final String externalId) {
    final EntityManager entityManager = createEntityManager();
    final User user = findUserByUsername(entityManager, externalId);
    try {
      if (user == null) {
        return false;
      }
      entityManager.getTransaction().begin();      
      entityManager.remove(user);
      entityManager.getTransaction().commit();
    } catch (final RuntimeException e) {
      entityManager.getTransaction().rollback();
      throw e;
    } finally {
      entityManager.close();
    }
    return true;
  }

  public User save(final String username) {
    final EntityManager entityManager = createEntityManager();
    entityManager.getTransaction().begin();
    try {
      LOGGER.info("Saving user " + username + "...");

      final User user = new User();
      user.setUsername(username);
      user.setFirstName(username);
      user.setBlocked("N");
      entityManager.persist(user);
      entityManager.getTransaction().commit();
      return user;
    } catch (final RuntimeException e) {
      entityManager.getTransaction().rollback();
      throw e;
    } finally {
      entityManager.close();
    }    
  }

  private EntityManager createEntityManager() {
    return this.entityManagerFactory.createEntityManager();
  }
}
