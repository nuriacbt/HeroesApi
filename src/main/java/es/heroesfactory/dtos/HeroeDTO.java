package es.heroesfactory.dtos;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String imageUrl;
    private Integer intelligence;
    private Integer speed;
    private Integer combat;
}
