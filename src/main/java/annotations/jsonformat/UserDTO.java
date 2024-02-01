package annotations.jsonformat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Setter;

import java.util.Date;

@Setter
class UserDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date registrationDate;

}
