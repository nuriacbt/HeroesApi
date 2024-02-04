package es.heroesfactory.services;

import es.heroesfactory.dtos.HeroeDTO;
import es.heroesfactory.exceptions.HeroeIdNotFoundException;
import es.heroesfactory.repositories.HeroesRepository;
import es.heroesfactory.repositories.entities.Heroe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeroesServicesTest {

    @InjectMocks
    private HeroesServiceImpl heroesService;
    @Mock
    private HeroesRepository heroesRepository;

    @Test
    public void getHeroeByIdExceptionTest() {
        Optional<Heroe> heroeOp = Optional.empty();
        when(heroesRepository.findById(55)).thenReturn(heroeOp);
        assertThrows(HeroeIdNotFoundException.class, () -> {
            heroesService.getHeroeById(55);
        });
    }

    @Test
    public void updateHeroeExceptionTest() {
        HeroeDTO heroe = HeroeDTO.builder().id(730).name("Zoom").imageUrl(null)
                .speed(55).intelligence(55).combat(55).build();
        when(heroesRepository.existsById(heroe.getId())).thenReturn(false);
        assertThrows(HeroeIdNotFoundException.class, () -> {
            heroesService.updateHeroe(heroe);
        });
    }

    @Test
    public void deleteHeroeExceptionTest() {
        when(heroesRepository.existsById(55)).thenReturn(false);
        assertThrows(HeroeIdNotFoundException.class, () -> {
            heroesService.deleteHeroe(55);
        });
    }
}
