package pl.rydzinski.spring.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.rydzinski.spring.HeroPackage.Character;

import java.util.List;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Long> {

    Character findByHeroId (Long id);
    Character findByHeroName (String name);
    List<Character> findAll ();
}
