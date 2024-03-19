package customdeserialization.with_annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * WRITE_ONLY fields are deserialized from JSON
 * READ_ONLY fields are serialized yo JSON
 */
@Getter
@Setter
@Builder
@ToString
class Items {

    @JsonProperty(value = "dish_name", access = JsonProperty.Access.WRITE_ONLY)
    private String dishName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Float price;

    @JsonProperty(value = "available", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isAvailable;

    @JsonProperty(value = "delicacy", access = JsonProperty.Access.READ_ONLY)
    private String delicacy;

    @JsonProperty(value = "orderable", access = JsonProperty.Access.READ_ONLY)
    private String orderable;

}
