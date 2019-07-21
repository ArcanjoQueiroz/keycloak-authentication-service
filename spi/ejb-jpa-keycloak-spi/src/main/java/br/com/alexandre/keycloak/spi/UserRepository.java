package br.com.alexandre.keycloak.spi;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

@Stateless
public class UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepository.class);

    @PersistenceContext(unitName = "ejb-jpa-keycloak-spi")
    protected EntityManager em;

    public boolean validateCredentials(final String username, final String password) {
        final String encryptedPassword = encrypt(password.toUpperCase());
        final User user = findUserByUsername(username);
        if (user == null) {
            logger.warn("User with username " + username + " not found");
            return false;
        }
        return user.getPassword() != null && user.getPassword().equals(encryptedPassword);
    }
    
    private String encrypt(final String plainPassword) {
      // TODO You need to implement the algorithm
      return plainPassword;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User updateCredentials(final String username, final String password) {
        final String encryptedPassword = encrypt(password.toUpperCase());
        final User user = findUserByUsername(username);
        if (user == null) {
            logger.warn("User with username " + username + " not found");
            return null;
        }
        user.setPassword(encryptedPassword);
        em.persist(user);
        return user;
    }

    public int getUsersCount() {
        final TypedQuery<Long> query = em.createNamedQuery("count", Long.class);
        return query.getSingleResult() != null ? query.getSingleResult().intValue() : 0;
    }

    public User findUserByUsernameOrEmail(final String usernameOrEmail) {
        return em.createNamedQuery("findByUsernameOrEmail", User.class)
                .setParameter("search", usernameOrEmail != null ? usernameOrEmail.trim().toLowerCase(): "")
                .setMaxResults(1)
                .getSingleResult();
    }

    public User findUserByUsername(final String username) {
        return em.createNamedQuery("findByUsername", User.class)
                .setParameter("username", username != null ? username.trim().toLowerCase(): "")
                .setMaxResults(1)
                .getSingleResult();
    }

    public User findUserByEmail(final String email) {
        return em.createNamedQuery("findByEmail", User.class)
                .setParameter("email", email != null ? email.trim().toLowerCase(): "")
                .setMaxResults(1)
                .getSingleResult();
    }

    public Collection<User> findAll() {
        return em.createNamedQuery("findAll", User.class)
                .getResultList();
    }

    public Collection<User> findAll(final int firstResult, final int maxResults) {
        return em.createNamedQuery("findAll", User.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }

    public Collection<User> findAllByUsernameOrEmail(final String search) {
        return em.createNamedQuery("findByUsernameOrEmail", User.class)
                .setParameter("search", search != null ? search.trim().toLowerCase(): "")
                .getResultList();
    }

    public Collection<User> findAllByUsernameOrEmail(final String search, final int firstResult, final int maxResults) {
        return em.createNamedQuery("findByUsernameOrEmail", User.class)
                .setParameter("search", search != null ? search.trim().toLowerCase(): "")
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean remove(final String externalId) {
        final User user = this.findUserByUsername(externalId);
        if (user == null) {
            return false;
        }
        em.remove(user);
        return true;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User save(final String username) {
        logger.info("Saving user " + username + "...");
        
        final User user = new User();
        user.setUsername(username);
        user.setFirstName(username);
        user.setBlocked("N");
        em.persist(user);
        
        return user;
    }

}
