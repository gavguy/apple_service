package apple.service.core.phone.Phone_Bean;

import apple.service.core.phone.model.ReservationEntity;
import apple.service.core.phone.model.StatusReservation;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class ManageBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private List<ReservationEntity> takenResult;

    private List<ReservationEntity> availableResult;


    public void prepare() {
        availableResult = new ArrayList<>();
        List<ReservationEntity> userReservations = em.createQuery(
                "select r from Reservation"
                        + " r " +
                        "where r.status = 'ACTIVE'", ReservationEntity.class)
                .getResultList();

        for (ReservationEntity r : userReservations) {
            Long reservationId = r.getId();
            Optional<ReservationEntity> firstReservation = em.createQuery(
                    "select r from Reservation r "
                            +   "where r.phone = :phone and r.status <> 'CLOSED' "
                            +  "order by r.created", ReservationEntity.class)
                    .setParameter("phone", r.getPhone())
                    .getResultStream()
                    .findFirst();
            if (firstReservation.isEmpty() || firstReservation.get().getId().equals(reservationId)) {
                availableResult.add(r);
            }
        }

        takenResult = em.createQuery("select r from Reservation r " +
                "where r.status = 'TAKEN'", ReservationEntity.class)
                .getResultList();
    }

    @Transactional
    public void giveBook(ReservationEntity reservation) {
        ReservationEntity r = em.merge(reservation);
        r.setStatus(StatusReservation.TAKEN);
        prepare();
    }

    @Transactional
    public void takeBook(ReservationEntity reservation) {
        ReservationEntity r = em.merge(reservation);
        r.setStatus(StatusReservation.CLOSED);
        prepare();
    }


    public List<ReservationEntity> getAvailableBooks() {
        return availableResult;
    }

    public List<ReservationEntity> getTakenBooks() {
        return takenResult;
    }

}
