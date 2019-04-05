package pl.rydzinski.spring.GameComponents;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.rydzinski.spring.UserPackage.User;

@Data
@NoArgsConstructor
public class NavBar {

    private H2 h2Nick;
    private H3 h3Lvl;
    private H3 h3Gold;
    private H3 h3Silver;
    private H3 h3Iron;
    private H3 h3Coal;
    private H3 h3Lether;
    private H3 h3Wool;

    private H2 h2NextLvl;
    private ProgressBar progressBarNextLvl;
    private H3 h3Tmp;

    public NavBar(String h2Nick) {
        initHLabelsResources(h2Nick);
        initProgressBar();
    }

    private void initHLabelsResources(String h2Nick) {
        this.h2Nick = new H2(h2Nick);
        this.h3Lvl = new H3();
        this.h3Gold = new H3();
        this.h3Silver = new H3();
        this.h3Iron = new H3();
        this.h3Coal = new H3();
        this.h3Lether = new H3();
        this.h3Wool = new H3();
        this.h2NextLvl = new H2();
    }

    private void initProgressBar() {
        this.progressBarNextLvl = new ProgressBar();
        this.progressBarNextLvl.setMin(0.0);
        this.progressBarNextLvl.addClassName("progressBarNextLvl");
        this.progressBarNextLvl.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
        this.h3Tmp = new H3("");
    }

    public void setPositionOfComponents(HorizontalLayout horizontalLayoutForResources, HorizontalLayout horizontalLayoutForExp) {
        horizontalLayoutForResources.add(h2Nick, h3Lvl, h3Gold, h3Silver, h3Iron, h3Coal, h3Lether, h3Wool);
        horizontalLayoutForExp.add(h2NextLvl, progressBarNextLvl, h3Tmp);
    }

    public void getActualResources(User user) {

        h3Lvl.setText("Level: " + user.getMainCharacter().getHeroLvl());
        h3Gold.setText("Gold: " + user.getMainCharacter().getHeroInventory().getAmountOfGold());
        h3Silver.setText("Silver: " + user.getMainCharacter().getHeroInventory().getAmountOfSilver());
        h3Iron.setText("Iron: " + user.getMainCharacter().getHeroInventory().getAmountOfIron());
        h3Coal.setText("Coal: " + user.getMainCharacter().getHeroInventory().getAmountOfCoal());
        h3Lether.setText("Leather: " + user.getMainCharacter().getHeroInventory().getAmountOfLeather());
        h3Wool.setText("Wool: " + user.getMainCharacter().getHeroInventory().getAmountOfWool());

        h2NextLvl.setText("Exp: " + user.getMainCharacter().getHeroExp() + "/" + user.getMainCharacter().getHeroExpToNextLvl());

        progressBarNextLvl.setMax(user.getMainCharacter().getHeroExpToNextLvl());
        progressBarNextLvl.setValue(user.getMainCharacter().getHeroExp());
    }
}
