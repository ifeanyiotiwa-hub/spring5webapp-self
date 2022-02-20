package guru.springframework.spring5webapp.dto;

import guru.springframework.spring5webapp.model.BaseItem;
import lombok.*;
import java.util.*;
import java.time.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDTO extends BaseItem {

    private String name;

    @Builder
    public CustomerDTO(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String name) {
        super(id, version, createdDate, lastModifiedDate);
        this.name = name;
    }
}

