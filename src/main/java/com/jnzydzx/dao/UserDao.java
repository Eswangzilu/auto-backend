package com.jnzydzx.dao;

import com.jnzydzx.entity.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class UserDao {
    @PersistenceContext(unitName = "ControlPU")
    private EntityManager em;

    /**
     * This function could insert A NEW USER to the database
     * and return this user which was attached to DB
     *
     * @param user
     * @return
     */
    public User addNewUser(User user) {
        em.persist(user);
        return user;
    }

    /**
     * This function was set to modify properties of A USER
     * and the return value may be a null, please look out !!
     *
     * @param user
     * @param id
     * @return
     */
    public User modifyUserById(User user, String id) {
        User userInDatabase = em.find(User.class, id);

        if (userInDatabase != null) {
            userInDatabase.copy(user);
        }

        return userInDatabase;
    }

    /**
     * There was a named-query that might be called here
     *
     * @return
     */
    public List<User> getAllUserInList() {
        TypedQuery<User> users = em.createNamedQuery("getAllUserInformation", User.class);
        return users.getResultList();
    }

    /**
     * This function can be used to delete a user which was
     * added to the database before, BUT THIS function can
     * ONLY delete a user that was bind to the Entity Manager !!
     *
     * @param id
     */
    public void deleteUser(String id) {
        User user = em.find(User.class, id);

        if (user != null) {
            em.remove(user);
        }
    }
}
