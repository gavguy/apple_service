package apple.service.core.phone.model;

import apple.service.core.auth.model.UserEntity;

import javax.persistence.*;

import java.io.Serializable;

import java.time.LocalDateTime;

@Entity(name = "Reservation")

@Table(name = "reservations")
public class ReservationEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private PhoneEntity book;
    @ManyToOne
    private UserEntity user;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusResarvation status;
    @Column
    private LocalDateTime created;
///somu
    @PrePersist
    public void onCreate() {
        created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhoneEntity getBook() {
        return book;
    }

    public void setBook(PhoneEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public StatusResarvation getStatus() {
        return status;
    }

    public void setStatus(StatusResarvation status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
