package apple.service.core.phone.Phone_Bean;

import apple.service.core.phone.model.PhoneEntity;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

@ViewScoped
@Named
public class NewBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private PhoneEntity phone = new PhoneEntity();
    private boolean created = false;

    @Transactional
    public String createBook() {
        em.persist(phone);
        phone = new PhoneEntity();
        created = true;
//        return "phone-created.xhtml";
        return null;
    }

    public boolean isCreated() {
        return created;
    }

    public PhoneEntity getPhone() {
        return phone;
    }

    public void setBook(PhoneEntity phone) {
        this.phone = phone;
    }

  

   
}
