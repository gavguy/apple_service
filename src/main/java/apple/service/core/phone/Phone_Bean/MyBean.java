package apple.service.core.phone.Phone_Bean;

import apple.service.core.auth.boundary.CurrentUser;
import apple.service.core.phone.model.PhoneEntity;
import apple.service.core.phone.model.ReservationEntity;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class MyBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CurrentUser currentUser;
    private List<ReservationEntity> availableResult;
    private List<ReservationEntity> inQueueResult;
    private List<ReservationEntity> takenResult;
    private List<PhoneEntity> historyResult;


    public void prepare() {
        availableResult = new ArrayList<>();
        inQueueResult = new ArrayList<>();
        List<ReservationEntity> userReservations = em.createQuery(
                "select r from Reservation  r " +
                        "where r.user = :user and r.status = 'ACTIVE'", ReservationEntity.class)
                .setParameter("user", currentUser.getUser())
                .getResultList();

        for (ReservationEntity r : userReservations) {
            Long reservationId = r.getId();
            Optional<ReservationEntity> firstReservation = em.createQuery(
                    "select r from Resevation r " +
                            "where r.phone = :phone and r.status <> 'CLOSED' " +
                            "order by r.created", ReservationEntity.class)
                    .setParameter("phone", r.getPhone())
                    .getResultStream()
                    .findFirst();
            if (firstReservation.isEmpty() || firstReservation.get().getId().equals(reservationId)) {
                availableResult.add(r);
            } else {
                inQueueResult.add(r);
            }
        }

        takenResult = em.createQuery("select r from Reservation r " +
                "where r.user = :user and r.status = 'TAKEN'", ReservationEntity.class)
                .setParameter("user", currentUser.getUser())
                .getResultList();

        historyResult = em.createQuery("select distinct r.phone from Reservation r " +
                "where r.user = :user and r.status = 'CLOSED'", PhoneEntity.class)
                .setParameter("user", currentUser.getUser())
                .getResultList();
    }

    public List<ReservationEntity> getAvailablePhones() {
        return availableResult;
    }

    public List<ReservationEntity> getInQueuePhones() {
        return inQueueResult;
    }

    public List<ReservationEntity> getTakenPhones() {
        return takenResult;
    }

    public List<PhoneEntity> getHistoryPhones() {
        return historyResult;
    }
}


