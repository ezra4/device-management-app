package SD.DeviceManagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class EnergyConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "timestamp",nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
    private Date time;

    @Column(name = "nrgCons",nullable = false)
    private int nrgCons;

    //@Getter(AccessLevel.NONE)
    //@Setter(AccessLevel.NONE)
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EnergyConsumption that = (EnergyConsumption) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
    return getClass().hashCode();
    }
}
