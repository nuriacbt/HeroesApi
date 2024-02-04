package es.heroesfactory.repositories.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "HEROES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Heroe implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "heroes_generator")
    @SequenceGenerator(name = "heroes_generator", sequenceName = "HEROES_SEQ", allocationSize = 1)
    @Column(name="ID")
    private Integer id;

    @Column(name="NAME")
    private String name;

    @Column(name="IMG_URL")
    private String imageUrl;

    @Column(name="INTELLIGENCE")
    private Integer intelligence;

    @Column(name="SPEED")
    private Integer speed;

    @Column(name="COMBAT")
    private Integer combat;

}
