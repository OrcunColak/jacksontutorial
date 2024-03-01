package annotations.circularreference.jsonmanagedreference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
class ParkingSpace {
    private Integer id;

    // This annotation is used on the "inverse" side of a bidirectional relationship.
    // It indicates that this property should be ignored during serialization to prevent circular references.
    @JsonBackReference
    private Employee employee;
}

