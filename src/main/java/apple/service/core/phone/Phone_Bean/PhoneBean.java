package apple.service.core.phone.Phone_Bean;

import apple.service.core.auth.boundary.CurrentUser;
import apple.service.core.phone.model.PhoneEntity;
import apple.service.core.phone.model.ReservationEntity;
import apple.service.core.phone.model.StatusResarvation;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

@ViewScoped
@Named
public class PhoneBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CurrentUser currentUser;
    private Long id;
    private PhoneEntity book;

    public void openBook() {
        System.out.println("Opening phone " + id);
        book = em.find(PhoneEntity.class, id);
    }

    @Transactional
    public void reserve(Long id) {
        System.out.println("Trying to reserve phone " + id
                + " for user " + currentUser.getUser().getId());

        PhoneEntity book = em.find(PhoneEntity.class, id);

        ReservationEntity reservation = new ReservationEntity();
        reservation.setBook(book);
        reservation.setUser(currentUser.getUser());
        reservation.setStatus(StatusResarvation.ACTIVE);

        em.persist(reservation);
    }

    public PhoneEntity getBook() {
        return book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
