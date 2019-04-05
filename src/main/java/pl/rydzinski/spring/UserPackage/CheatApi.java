package pl.rydzinski.spring.UserPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rydzinski.spring.HeroPackage.Character;
import pl.rydzinski.spring.HeroPackage.Inventory;
import pl.rydzinski.spring.Repositories.CharacterRepository;
import pl.rydzinski.spring.Repositories.InventoryRepository;

@RestController
@RequestMapping("/cheat")
public class CheatApi {

    private InventoryRepository inventoryRepository;
    private CharacterRepository characterRepository;

    @Autowired
    public CheatApi(InventoryRepository inventoryRepository, CharacterRepository characterRepository) {
        this.inventoryRepository = inventoryRepository;
        this.characterRepository = characterRepository;
    }

    @GetMapping("{heroname}/level/{level}")
    public boolean cheatHeroLvl(@PathVariable("heroname") String heroname, @PathVariable("level") int lvl) {
        try {
            Character character = characterRepository.findByHeroName(heroname);
            character.setHeroLvl(lvl);
            characterRepository.save(character);
            return true;
        } catch (Exception E) {
            return false;
        }

    }

    @GetMapping("{heroname}/resources/{amount}")
    public boolean cheatHeroItemsLvl(@PathVariable("heroname") String heroname, @PathVariable("amount") int amount) {
        try {
            Character character = characterRepository.findByHeroName(heroname);
            Inventory inventory = inventoryRepository.findByInventoryId(character.getHeroId());
            character.setHeroInventory(inventory);
            character.getHeroInventory().setAmountOfWool(amount);
            character.getHeroInventory().setAmountOfLeather(amount);
            character.getHeroInventory().setAmountOfCoal(amount);
            character.getHeroInventory().setAmountOfIron(amount);
            character.getHeroInventory().setAmountOfSilver(amount);
            character.getHeroInventory().setAmountOfGold(amount);
            characterRepository.save(character);
            inventoryRepository.save(inventory);
            return true;
        } catch (Exception E) {
            return false;
        }
    }
}
