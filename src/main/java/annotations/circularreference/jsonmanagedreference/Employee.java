package annotations.circularreference.jsonmanagedreference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
class Employee {
    private Integer id;

    // This annotation is used on the "owner" side of a bidirectional relationship.
    // It indicates that this property is the one that should be serialized normally.
    @JsonManagedReference
    private ParkingSpace parkingSpace;
}
