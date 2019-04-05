package pl.rydzinski.spring.HeroPackage;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private int itemLvl;
    @Enumerated(EnumType.STRING)
    private TypeOfItem typeOfItem;
    @Enumerated(EnumType.STRING)
    private TypeOfResourse typeOfResourseToUpgrade1;
    @Enumerated(EnumType.STRING)
    private TypeOfResourse typeOfResourseToUpgrade2;

    public Item(TypeOfItem typeOfItem) {

        this.itemLvl = 0;
        this.typeOfItem = typeOfItem;

        switch (typeOfItem) {
            case SWORD:
                typeOfResourseToUpgrade1 = TypeOfResourse.IRON;
                typeOfResourseToUpgrade2 = TypeOfResourse.COAL;
                break;
            case POCKET:
                typeOfResourseToUpgrade1 = TypeOfResourse.GOLD;
                typeOfResourseToUpgrade2 = TypeOfResourse.WOOL;
                break;
            case BOOTS:
                typeOfResourseToUpgrade1 = TypeOfResourse.SILVER;
                typeOfResourseToUpgrade2 = TypeOfResourse.LEATHER;
                break;
        }
    }

    public int getAmountResToUpdate() {
        return (itemLvl+1)*(itemLvl+1);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemLvl=" + itemLvl +
                ", typeOfItem=" + typeOfItem +
                '}';
    }
}
