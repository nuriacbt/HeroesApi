package es.heroesfactory.mappers;

import es.heroesfactory.dtos.HeroeDTO;
import es.heroesfactory.repositories.entities.Heroe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class HeroesMapperTest {
    private HeroesMapper heroesMapper = Mappers.getMapper(HeroesMapper.class);

    @Test
    public void passEntityToDtoTest() {
        Heroe entity = Heroe.builder().id(730).name("Zoom").imageUrl(null)
                .speed(55).intelligence(55).combat(55).build();
        HeroeDTO dto = heroesMapper.entityToDTO(entity);
        asserts(dto, entity);
    }

    @Test
    public void passDtoToEntityTest() {
        HeroeDTO dto = HeroeDTO.builder().id(730).name("Zoom").imageUrl(null)
                .speed(55).intelligence(55).combat(55).build();
        Heroe entity = heroesMapper.dtoToEntity(dto);
        asserts(dto, entity);
    }

    private void asserts(HeroeDTO dto, Heroe entity) {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getImageUrl(), entity.getImageUrl());
        assertEquals(dto.getIntelligence(), entity.getIntelligence());
        assertEquals(dto.getSpeed(), entity.getSpeed());
        assertEquals(dto.getCombat(), entity.getCombat());
    }
}
