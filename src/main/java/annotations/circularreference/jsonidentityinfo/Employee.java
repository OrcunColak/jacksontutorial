package annotations.circularreference.jsonidentityinfo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Employee.class)

@Getter
@Setter
class Employee {
    private Integer id;

    private ParkingSpace parkingSpace;
}
