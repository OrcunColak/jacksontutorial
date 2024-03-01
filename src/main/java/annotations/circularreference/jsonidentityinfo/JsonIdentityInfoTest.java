package annotations.circularreference.jsonidentityinfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class JsonIdentityInfoTest {

    public static void main(String[] args) throws JsonProcessingException {

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(9);

        Employee employee = new Employee();
        employee.setId(1);
        employee.setParkingSpace(parkingSpace);

        parkingSpace.setEmployee(employee);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(employee);

        // {"id":1,"parkingSpace":{"id":9,"employee":1}}
        log.info(jsonString);
    }
}
