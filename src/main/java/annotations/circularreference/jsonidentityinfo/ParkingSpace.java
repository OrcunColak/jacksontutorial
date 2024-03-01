package annotations.circularreference.jsonidentityinfo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = ParkingSpace.class)

@Getter
@Setter
class ParkingSpace {
    private Integer id;

    private Employee employee;
}

