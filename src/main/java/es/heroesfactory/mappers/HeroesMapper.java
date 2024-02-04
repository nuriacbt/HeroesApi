package es.heroesfactory.mappers;

import es.heroesfactory.dtos.HeroeDTO;
import es.heroesfactory.repositories.entities.Heroe;
import java.util.List;

import org.mapstruct.Mapper;

@Mapper
public interface HeroesMapper {

    HeroeDTO entityToDTO(Heroe entity);

    Heroe dtoToEntity(HeroeDTO dto);

    List<HeroeDTO> entityListToDTOList(List<Heroe> list);
}
