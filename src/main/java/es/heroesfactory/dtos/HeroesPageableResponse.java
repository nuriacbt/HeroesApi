package es.heroesfactory.dtos;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroesPageableResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer numElements;
    private Integer numPage;
    private Long totalElements;
    private Integer totalPages;

    private List<HeroeDTO> heroes;

}
