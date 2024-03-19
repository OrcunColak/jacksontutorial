package customdeserialization.with_annotation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@JsonDeserialize(using = MenuItemDeserializer.class)
class MenuItem {
    private List<Items> items;
}
