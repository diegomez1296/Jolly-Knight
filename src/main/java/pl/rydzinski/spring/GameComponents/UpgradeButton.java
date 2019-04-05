package pl.rydzinski.spring.GameComponents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.rydzinski.spring.HeroPackage.Item;

@Data
@NoArgsConstructor
public class UpgradeButton {

    private Item upgradeButtonItem;
    private String upgradeButtonImage;

    private Button upgradeButton;

    private H4 notificationTitle;
    private H4 notificationContentBonus;
    private H5 notificationContentLvl;
    private H4 notificationContentNeedToUpgrate;
    private H5 notificationContentAmountItemsToUpg1;
    private H5 notificationContentAmountItemsToUpg2;
    private NativeButton notificationButtonUpgrade;
    private NativeButton notificationButtonClose;
    private Notification notification;

    public UpgradeButton(Item upgradeButtonItem, String upgradeButtonWidth, String upgradeButtonHeight) {
        this.upgradeButtonItem = upgradeButtonItem;
        switch (upgradeButtonItem.getTypeOfItem()) {
            case SWORD:
                upgradeButtonImage = "icons\\SwordIcon.png";
                break;
            case POCKET:
                upgradeButtonImage = "icons\\PocketIcon.png";
                break;
            case BOOTS:
                upgradeButtonImage = "icons\\BootsIcon.png";
                break;
        }

        this.upgradeButton = new Button(new Image(upgradeButtonImage, upgradeButtonItem.getTypeOfItem().name()));
        this.upgradeButton.getStyle().set("--lumo-button-size", "0px");
        this.upgradeButton.setWidth(upgradeButtonWidth);
        this.upgradeButton.setHeight(upgradeButtonHeight);

        initNotification(upgradeButtonItem);

        upgradeButton.addClickListener(buttonClickEvent -> upgradeButtonNotificOpen());
        notificationButtonClose.addClickListener(nativeButtonClickEvent -> notification.close());
    }

    private void initNotification(Item upgradeButtonItem) {
        this.notificationTitle = new H4("Upgrade " + upgradeButtonItem.getTypeOfItem() + "!");
        this.notificationTitle.addClassName("upgr-notif-title");
        this.notificationContentBonus = new H4();
        this.notificationContentBonus.addClassName("upgr-notif-title");
        this.notificationContentLvl = new H5();
        this.notificationContentLvl.setClassName("notif-hX");
        this.notificationContentNeedToUpgrate = new H4("Need to upgrade");
        this.notificationContentNeedToUpgrate.addClassName("upgr-notif-title");
        this.notificationContentAmountItemsToUpg1 = new H5();
        this.notificationContentAmountItemsToUpg2 = new H5();
        this.notificationButtonUpgrade = new NativeButton("Upgrade");
        this.notificationButtonUpgrade.setClassName("upg-butt-notif");
        this.notificationButtonClose = new NativeButton("Close");
        this.notification = new Notification(notificationTitle,notificationContentBonus,notificationContentLvl,notificationContentNeedToUpgrate,notificationContentAmountItemsToUpg1, notificationContentAmountItemsToUpg2, notificationButtonUpgrade, notificationButtonClose);
        this.notification.setDuration(60000);
    }

    private void upgradeButtonNotificOpen() {

        notificationContentLvl.setText("Level: " + upgradeButtonItem.getItemLvl() + descriptionOfBonus());
        if (upgradeButtonItem.getItemLvl() < 3) {
        notificationContentAmountItemsToUpg1.setText(upgradeButtonItem.getTypeOfResourseToUpgrade1().name() +": " + (upgradeButtonItem.getAmountResToUpdate()));
        notificationContentAmountItemsToUpg2.setText(upgradeButtonItem.getTypeOfResourseToUpgrade2().name() +": " + (upgradeButtonItem.getAmountResToUpdate()));
        }
        else {
            notificationContentAmountItemsToUpg1.setText("Max level achieved");
            notificationContentAmountItemsToUpg2.setText("");
        }
        notification.open();
    }

    private String descriptionOfBonus() {

        String descriptionOfBonus="";
        switch (upgradeButtonItem.getTypeOfItem()) {
            case SWORD:
                descriptionOfBonus = "   (Bonus Experience: +" + (upgradeButtonItem.getItemLvl()*upgradeButtonItem.getItemLvl()*10) + "%)";
                break;
            case POCKET:
                descriptionOfBonus = "   (Max extra items: +"+ (upgradeButtonItem.getItemLvl()*upgradeButtonItem.getItemLvl())+")";
                break;
            case BOOTS:
                descriptionOfBonus = "   (Mission Time: -"+ (upgradeButtonItem.getItemLvl()*upgradeButtonItem.getItemLvl()*10) + "%)";
                break;
        }
        return descriptionOfBonus;
    }
}
