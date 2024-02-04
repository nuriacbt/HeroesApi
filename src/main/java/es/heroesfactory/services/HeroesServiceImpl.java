package es.heroesfactory.services;

import es.heroesfactory.dtos.AllHeroesResponse;
import es.heroesfactory.dtos.HeroeDTO;
import es.heroesfactory.dtos.HeroesPageableResponse;
import es.heroesfactory.exceptions.HeroeIdNotFoundException;
import es.heroesfactory.exceptions.PageDataNotFoundException;
import es.heroesfactory.repositories.entities.Heroe;
import es.heroesfactory.mappers.HeroesMapper;
import es.heroesfactory.repositories.HeroesRepository;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static es.heroesfactory.config.CacheConfigurator.CACHE_NAME;

@Service
@AllArgsConstructor
public class HeroesServiceImpl implements HeroesService {

    @Resource
    private HeroesRepository heroesRepository;

    @Resource
    private HeroesMapper heroesMapper;

    @Override
    @Cacheable(value = CACHE_NAME)
    public AllHeroesResponse getAllHeroes() {
        return AllHeroesResponse.builder().heroes(this.heroesMapper.entityListToDTOList(
                this.heroesRepository.findAll())).build();
    }

    @Override
    public HeroesPageableResponse getAllHeroesPage(Integer page, Integer elements) {
        Pageable pageable = PageRequest.of(page-1, elements, Sort.by("id").ascending());
        Page<Heroe> pageHeroes = this.heroesRepository.findAll(pageable);

        if (!pageHeroes.hasContent()) {
            throw new PageDataNotFoundException("Incorrect page data");
        }

        return HeroesPageableResponse.builder()
                .numPage(page)
                .numElements(elements)
                .totalElements(pageHeroes.getTotalElements())
                .totalPages(pageHeroes.getTotalPages())
                .heroes(this.heroesMapper.entityListToDTOList(pageHeroes.getContent()))
                .build();
    }

    @Override
    public HeroeDTO getHeroeById(Integer id) {
        Optional<Heroe> heroeOp = this.heroesRepository.findById(id);
        if(heroeOp.isEmpty()) {
            throw new HeroeIdNotFoundException("Heroe not found");
        }
        return this.heroesMapper.entityToDTO(heroeOp.get());
    }

    @Override
    public AllHeroesResponse searchHeroesByName(String name) {
        return AllHeroesResponse.builder().heroes(this.heroesMapper.entityListToDTOList(
                this.heroesRepository.findByName(name.toUpperCase()))).build();
    }

    @Override
    @Caching(evict  = { @CacheEvict(value=CACHE_NAME, allEntries=true) })
    public void saveHeroe(HeroeDTO heroe) {
        this.heroesRepository.save(this.heroesMapper.dtoToEntity(heroe));
    }

    @Override
    @Caching(evict  = { @CacheEvict(value=CACHE_NAME, allEntries=true) })
    public void updateHeroe(HeroeDTO heroe) {
        if(!this.heroesRepository.existsById(heroe.getId())) {
            throw new HeroeIdNotFoundException("This heroe could not be updated because his id is incorrect");
        }
        this.heroesRepository.save(this.heroesMapper.dtoToEntity(heroe));
    }

    @Override
    @Caching(evict  = { @CacheEvict(value=CACHE_NAME, allEntries=true) })
    public void deleteHeroe(Integer id) {
        if(!this.heroesRepository.existsById(id)) {
            throw new HeroeIdNotFoundException("This heroe could not be removed because his id is incorrect");
        }
        this.heroesRepository.deleteById(id);
    }
}
