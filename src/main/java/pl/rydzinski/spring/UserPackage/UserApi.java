package pl.rydzinski.spring.UserPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rydzinski.spring.AspectPackage.AOPAdvice;
import pl.rydzinski.spring.HeroPackage.Character;
import pl.rydzinski.spring.HeroPackage.*;
import pl.rydzinski.spring.Repositories.CharacterRepository;
import pl.rydzinski.spring.Repositories.InventoryRepository;
import pl.rydzinski.spring.Repositories.ItemRepository;
import pl.rydzinski.spring.Repositories.UserRepository;

@RestController
@RequestMapping("/admin")
public class UserApi {

    private ItemRepository itemRepository;
    private InventoryRepository inventoryRepository;
    private CharacterRepository characterRepository;
    private UserRepository userRepository;

    @Autowired
    public UserApi(ItemRepository itemRepository, InventoryRepository inventoryRepository,
                   CharacterRepository characterRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.inventoryRepository = inventoryRepository;
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/get/{username}")
    public String getAccountByUserName(@PathVariable("username") String username) {

        try {
            User user = userRepository.findByUserName(username);
            Character character = characterRepository.findByHeroId(user.getUserId());
            Inventory inventory = inventoryRepository.findByInventoryId(character.getHeroId());
            Item itemSword = itemRepository.findByItemId((character.getHeroId() * 3) - 2);
            Item itemPocket = itemRepository.findByItemId((character.getHeroId() * 3) - 1);
            Item itemBoots = itemRepository.findByItemId((character.getHeroId() * 3));

            return "User: " + user.toString() + "\n" +
                    "Hero: " + character.toString()  + "\n" +
                    "Inventory: "  + inventory.toString() + "\n" +
                    "Sword: " + itemSword.toString() + "\n" +
                    "Pocket: " + itemPocket.toString() + "\n" +
                    "Boots: " + itemBoots.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    @AOPAdvice
    @GetMapping("/get")
    public String getAllUsers() {
        return characterRepository.findAll().toString();
    }

}
