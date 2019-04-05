package pl.rydzinski.spring.HeroPackage;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.rydzinski.spring.UserPackage.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Entity
@NoArgsConstructor
public class Character {

    @Transient
    private Random rand = new Random();

    @Transient
    private final int minLvl = 1;
    @Transient
    private final int maxLvl = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heroId;
    private String heroName;
    private int heroLvl;
    private int heroExp;
    @OneToMany
    @JoinColumn(name = "heroId")
    private List<Item> heroItems;
    @OneToOne
    private Inventory heroInventory;
    @OneToOne(mappedBy = "mainCharacter")
    private User user;

    private Long heroMissionId;
    private boolean isOnMission;
    private Long timeToEndMission;

    public Character(String userNick, List<Item> heroItems, Inventory inventory) {

        this.heroName = userNick;
        this.heroLvl = minLvl;
        this.heroExp = 0;

        this.heroItems = heroItems;

        heroMissionId = 0L;
        isOnMission = false;
        timeToEndMission = 0L;

        this.heroInventory = inventory;

    }

    public int getHeroExpToNextLvl() {
        return heroLvl * heroLvl * 50;
    }

    public List<String> getAwards(Long actualMissionId, int actualMissionExp) {

        //Exp
        int awardExp = heroExp;
        heroExp += actualMissionExp + (actualMissionExp * (heroItems.get(0).getItemLvl() * heroItems.get(0).getItemLvl() * 0.1));
        awardExp = heroExp - awardExp;
        checkNextLvl();

        //Resources
        List<TypeOfResourse> listOfResources = new ArrayList<>();
        //1-Gold, 2-Silver, 3-Iron, 4-Coal, 5-Leather, 6- Wool
        for (int i = 0; i < actualMissionId * 2 + (heroItems.get(1).getItemLvl()*heroItems.get(1).getItemLvl()); i++) {
            int randItem = rand.nextInt(6) + 1;
            int randIsOur = rand.nextInt(100);
            switch (randItem) {
                case 1:
                    if (randIsOur < (randItem * 10 * actualMissionId)) {
                        listOfResources.add(TypeOfResourse.GOLD);
                        heroInventory.setAmountOfGold(getHeroInventory().getAmountOfGold()+1);
                    }
                    break;
                case 2:
                    if (randIsOur < (randItem * 10 * actualMissionId)) {
                        listOfResources.add(TypeOfResourse.SILVER);
                        heroInventory.setAmountOfSilver(getHeroInventory().getAmountOfSilver()+1);
                    }
                    break;
                case 3:
                    if (randIsOur < (randItem * 10 * actualMissionId)) {
                        listOfResources.add(TypeOfResourse.IRON);
                        heroInventory.setAmountOfIron(getHeroInventory().getAmountOfIron()+1);
                    }
                    break;
                case 4:
                    if (randIsOur < (randItem * 10 * actualMissionId)) {
                        listOfResources.add(TypeOfResourse.COAL);
                        heroInventory.setAmountOfCoal(getHeroInventory().getAmountOfCoal()+1);
                    }
                    break;
                case 5:
                    if (randIsOur < (randItem * 10 * actualMissionId)) {
                        listOfResources.add(TypeOfResourse.LEATHER);
                        heroInventory.setAmountOfLeather(getHeroInventory().getAmountOfLeather()+1);
                    }
                    break;
                case 6:
                    if (randIsOur < (randItem * 10 * actualMissionId)) {
                        listOfResources.add(TypeOfResourse.WOOL);
                        heroInventory.setAmountOfWool(getHeroInventory().getAmountOfWool()+1);
                    }
                    break;
            }
            isOnMission = false;
        }

        List<String> result = new ArrayList<>();
        result.add("Exp: +" + awardExp);
        result.add("Amount Of Items: "+ listOfResources.size());
        result.add("New Items: "+listOfResources.toString());
        return result;
    }

    private void checkNextLvl() {
        if (heroExp >= (heroLvl * heroLvl * 50) && heroLvl < maxLvl) {
            heroExp -= (heroLvl * heroLvl * 50);
            heroLvl++;
        }
        else if (heroExp >= (heroLvl * heroLvl * 50) && heroLvl == maxLvl) {
            heroExp = (heroLvl * heroLvl * 50);
        }
    }

    @Override
    public String toString() {
        return "Character{" +
                "heroName='" + heroName + '\'' +
                ", heroLvl=" + heroLvl +
                ", heroExp=" + heroExp +
                ", isOnMission=" + isOnMission +
                ", timeToEndMission=" + timeToEndMission +
                '}';
    }
}
