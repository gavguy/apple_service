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
    private PhoneEntity phone;
    @ManyToOne
    private UserEntity user;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusReservation status;
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

    public PhoneEntity getPhone() {
        return phone;
    }

    public void setPhone(PhoneEntity phone) {
        this.phone = phone;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public StatusReservation getStatus() {
        return status;
    }

    public void setStatus(StatusReservation status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    
}
