package apple.service.core.phone.Phone_Bean;

import apple.service.core.phone.model.PhoneEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ListBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private String term;
    private boolean delete=false;
    //vedj tak nado
private Long id;
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

   @Transactional
    public String phonedelete () {
       PhoneEntity phone;
       phone = em.find(PhoneEntity.class, id);
       em.remove(phone);
       delete = true;
       return null;
   }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
