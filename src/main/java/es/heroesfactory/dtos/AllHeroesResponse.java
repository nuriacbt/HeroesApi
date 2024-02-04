package es.heroesfactory.dtos;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllHeroesResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<HeroeDTO> heroes;
}
