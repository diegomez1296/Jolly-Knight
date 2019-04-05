package pl.rydzinski.spring.HeroPackage;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Inventory {

    @Transient
    private final int MAX_AMOUNT = 99;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    private int amountOfGold;
    private int amountOfSilver;
    private int amountOfIron;
    private int amountOfCoal;
    private int amountOfLeather;
    private int amountOfWool;
    @OneToOne(mappedBy = "heroInventory")
    private Character hero;


    public Inventory() {
        this.amountOfGold = 0;
        this.amountOfSilver = 0;
        this.amountOfIron = 0;
        this.amountOfCoal = 0;
        this.amountOfLeather = 0;
        this.amountOfWool = 0;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "amountOfGold=" + amountOfGold +
                ", amountOfSilver=" + amountOfSilver +
                ", amountOfIron=" + amountOfIron +
                ", amountOfCoal=" + amountOfCoal +
                ", amountOfLeather=" + amountOfLeather +
                ", amountOfWool=" + amountOfWool +
                '}';
    }

    public void setAmountOfGold(int amountOfGold) {
        this.amountOfGold = (amountOfGold > MAX_AMOUNT) ? MAX_AMOUNT : amountOfGold;
    }

    public void setAmountOfSilver(int amountOfSilver) {
        this.amountOfSilver = (amountOfSilver > MAX_AMOUNT) ? MAX_AMOUNT : amountOfSilver;
    }

    public void setAmountOfIron(int amountOfIron) {
        this.amountOfIron = (amountOfIron > MAX_AMOUNT) ? MAX_AMOUNT : amountOfIron;
    }

    public void setAmountOfCoal(int amountOfCoal) {
        this.amountOfCoal = (amountOfCoal > MAX_AMOUNT) ? MAX_AMOUNT : amountOfCoal;
    }

    public void setAmountOfLeather(int amountOfLeather) {
        this.amountOfLeather = (amountOfLeather > MAX_AMOUNT) ? MAX_AMOUNT : amountOfLeather;
    }

    public void setAmountOfWool(int amountOfWool) {
        this.amountOfWool = (amountOfWool > MAX_AMOUNT) ? MAX_AMOUNT : amountOfWool;
    }
}
