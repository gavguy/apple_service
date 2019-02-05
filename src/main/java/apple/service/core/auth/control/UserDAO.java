package apple.service.core.auth.control;

import apple.service.core.auth.model.UserEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
public class UserDAO {
    @PersistenceContext
    private EntityManager em;

    public Optional<UserEntity> findUser(String name) {
        return em.createQuery("select u from User u where u.loginName = :loginName", UserEntity.class)
                .setParameter("loginName", name)
                .getResultStream()
                .findFirst();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createUser(UserEntity user) {
        em.persist(user);
    }

}
