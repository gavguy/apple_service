package apple.service.core.phone.Phone_Bean;

import apple.service.core.phone.model.PhoneEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ListBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private String term;

    public List<PhoneEntity> getPhones() {
        if (term == null) {
            return em.createQuery("select p from Phone p", PhoneEntity.class)
                    .getResultList();
        } else {
            return em.createQuery("select p from Phone p where lower(p.owner) like :term",
                    PhoneEntity.class)
                    .setParameter("term", "%" + term.toLowerCase() + "%")
                    .getResultList();
        }
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void doSearch() {
        System.out.println("Searching");
    }
}
