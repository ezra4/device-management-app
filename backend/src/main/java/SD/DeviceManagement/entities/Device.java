package SD.DeviceManagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Device {
    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "address",nullable = false)
    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Device device = (Device) o;
        return id != null && Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
