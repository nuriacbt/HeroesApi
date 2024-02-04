package es.heroesfactory.services;

import es.heroesfactory.dtos.AllHeroesResponse;
import es.heroesfactory.dtos.HeroeDTO;
import es.heroesfactory.dtos.HeroesPageableResponse;

import java.util.List;

public interface HeroesService {

    AllHeroesResponse getAllHeroes();

    HeroesPageableResponse getAllHeroesPage(Integer page, Integer elements);

    HeroeDTO getHeroeById(Integer id);

    AllHeroesResponse searchHeroesByName(String name);

    void saveHeroe(HeroeDTO heroe);

    void updateHeroe(HeroeDTO heroe);

    void deleteHeroe(Integer id);
}
