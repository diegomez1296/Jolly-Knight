package pl.rydzinski.spring.GUI;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.rydzinski.spring.GameComponents.Mission;
import pl.rydzinski.spring.GameComponents.MissionNotification;
import pl.rydzinski.spring.GameComponents.NavBar;
import pl.rydzinski.spring.GameComponents.UpgradeButton;
import pl.rydzinski.spring.HeroPackage.Character;
import pl.rydzinski.spring.HeroPackage.Inventory;
import pl.rydzinski.spring.HeroPackage.Item;
import pl.rydzinski.spring.HeroPackage.TypeOfItem;
import pl.rydzinski.spring.Repositories.*;
import pl.rydzinski.spring.UserPackage.User;

import java.util.ArrayList;
import java.util.List;

@Route(value = "game")
@StyleSheet("frontend://styles/style.css")
public class MainView extends VerticalLayout {

    //region UserData
    private User user;
    private String userName;
    private String userPasswd;
    private String userEmail;
    private String userAvatar;
    private LoginGUI loginGUI;
    private boolean isAccountWasFound;
    //endregion

    //region Items
    private Item itemSword;
    private Item itemPocket;
    private Item itemBoots;
    private List<Item> itemList;
    //endregion

    //region ActualMissionData
    private Long actualMissionId;
    private Long actualMissionTime;
    private int actualMissionExp;
    //endregion

    //region Repositories
    private UserRepository userRepository;
    private CharacterRepository characterRepository;
    private InventoryRepository inventoryRepository;
    private ItemRepository itemRepository;
    //endregion

    //region Layouts
    private HorizontalLayout horizontalLayoutForResources;
    private HorizontalLayout horizontalLayoutForExp;
    private VerticalLayout verticalLayoutForUpgButtons;
    private HorizontalLayout horizontalLayoutForAvatar;
    private VerticalLayout verticalLayoutForMissions;
    private HorizontalLayout horizontalLayoutForMissions;
    //endregion

    //region GameComponents
    private NavBar navBar;
    private Image imageAvatar;
    private List<Mission> listOfMissions;
    private List<UpgradeButton> listOfUpgradeButtons;
    //endregion

    //region Mission Components
    private Tabs missions;
    private Div missionDetails;
    private Image missionBackground;
    private Button missionButton;
    private boolean isVisibleMissionButton;

    private MissionNotification missionNotification;
    //endregion

    @Autowired
    public MainView(UserRepository userRepository, CharacterRepository characterRepository, InventoryRepository inventoryRepository, ItemRepository itemRepository) {

        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
        this.inventoryRepository = inventoryRepository;
        this.itemRepository = itemRepository;

        setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
        setSizeFull();
        addClassName("main-view");

        initComponents();
    }

    private void initComponents() {
        initUserAccount();
        initServerTimer();
        initLayouts();
        initNavBar();
        initImageAvatar();
        initUpgradeButtons();
        initMissions();
        setPositionOfComponents();
        getActualResources();
    }

    private void initUserAccount() {
        try {
            loginGUI = new LoginGUI();
            userName = loginGUI.getUserData().get(0);
            userPasswd = loginGUI.getUserData().get(1);
            userEmail = loginGUI.getUserData().get(2);
            userAvatar = loginGUI.getUserData().get(3);
            isAccountWasFound = true;
        } catch (Exception e) {
            userName = "TestCharacter";
            userPasswd = "";
            userEmail = "";
            userAvatar = "icons\\KnightRed.jpg";
            isAccountWasFound = false;
        }

        try {
            loginIntoAccount(userName);
        } catch (Exception e) {
            createAccount(userName, userPasswd, userEmail);
        }
    }

    private void loginIntoAccount(String username) {
        itemList = new ArrayList<>();

        user = getUser(username);
        user.setMainCharacter(getHero(username));
        user.getMainCharacter().setHeroInventory(getInventory(username));
        itemList = getItemList(username);
        user.getMainCharacter().setHeroItems(itemList);
    }

    private void createAccount(String username, String password, String email) {

        itemSword = new Item(TypeOfItem.SWORD);
        itemPocket = new Item(TypeOfItem.POCKET);
        itemBoots = new Item(TypeOfItem.BOOTS);

        itemList = new ArrayList<>();
        itemList.add(itemSword);
        itemList.add(itemPocket);
        itemList.add(itemBoots);

        Inventory inventory = new Inventory();
        Character character = new Character(username, itemList, inventory);
        user = new User(username, password, email, character);

        itemRepository.save(user.getMainCharacter().getHeroItems().get(0));
        itemRepository.save(user.getMainCharacter().getHeroItems().get(1));
        itemRepository.save(user.getMainCharacter().getHeroItems().get(2));
        inventoryRepository.save(user.getMainCharacter().getHeroInventory());
        characterRepository.save(user.getMainCharacter());
        userRepository.save(user);
    }

    private void initServerTimer() {
        UI ui = UI.getCurrent();
        ui.setPollInterval(1000);
        ui.addPollListener(pollEvent -> {
            if (user.getMainCharacter().getTimeToEndMission() > 0) {
                user.getMainCharacter().setTimeToEndMission(user.getMainCharacter().getTimeToEndMission() - 1);
                characterRepository.save(user.getMainCharacter());
                if (isVisibleMissionButton) {
                    missionButton.setText(user.getMainCharacter().getTimeToEndMission() + " sek to end mission");
                    if (user.getMainCharacter().getTimeToEndMission() == 0L) {
                        missionButton.setText("Mission Completed!");
                        missionButton.setEnabled(true);
                    }
                }
            }
        });
    }

    private void initLayouts() {
        horizontalLayoutForResources = new HorizontalLayout();
        horizontalLayoutForResources.setWidth("100%");
        horizontalLayoutForExp = new HorizontalLayout();
        horizontalLayoutForExp.setWidth("100%");
        verticalLayoutForUpgButtons = new VerticalLayout();
        verticalLayoutForUpgButtons.setWidth("15%");
        verticalLayoutForUpgButtons.addClassName("upg-buttons-layout");
        horizontalLayoutForAvatar = new HorizontalLayout();
        horizontalLayoutForAvatar.setWidth("100%");
        verticalLayoutForMissions = new VerticalLayout();
        verticalLayoutForMissions.addClassName("verticalLayoutForMissions");
        horizontalLayoutForMissions = new HorizontalLayout();
    }

    private void initNavBar() {
        navBar = new NavBar(user.getMainCharacter().getHeroName());
    }

    private void initImageAvatar() {
        imageAvatar = new Image(userAvatar, "Avatar");
        imageAvatar.addClassName("imageAvatar");
    }

    private void initUpgradeButtons() {
        listOfUpgradeButtons = new ArrayList<>();
        if (isAccountWasFound) {
            listOfUpgradeButtons.add(new UpgradeButton(user.getMainCharacter().getHeroItems().get(0), "75px", "75px"));
            listOfUpgradeButtons.add(new UpgradeButton(user.getMainCharacter().getHeroItems().get(1), "75px", "75px"));
            listOfUpgradeButtons.add(new UpgradeButton(user.getMainCharacter().getHeroItems().get(2), "75px", "75px"));
        } else {
            listOfUpgradeButtons.add(new UpgradeButton(new Item(TypeOfItem.SWORD), "75px", "75px"));
            listOfUpgradeButtons.add(new UpgradeButton(new Item(TypeOfItem.POCKET), "75px", "75px"));
            listOfUpgradeButtons.add(new UpgradeButton(new Item(TypeOfItem.BOOTS), "75px", "75px"));
        }

        for (UpgradeButton upgradeButton : listOfUpgradeButtons) {
            upgradeButton.getNotificationButtonUpgrade().addClickListener(nativeButtonClickEvent -> upgradeItem(upgradeButton));
        }
    }

    private void upgradeItem(UpgradeButton upgradeButton) {
        switch (upgradeButton.getUpgradeButtonItem().getTypeOfItem()) {
            case SWORD:
                if (user.getMainCharacter().getHeroInventory().getAmountOfIron() >= upgradeButton.getUpgradeButtonItem().getAmountResToUpdate() && user.getMainCharacter().getHeroInventory().getAmountOfCoal() >= upgradeButton.getUpgradeButtonItem().getAmountResToUpdate() && user.getMainCharacter().getHeroItems().get(0).getItemLvl() < 3) {
                    user.getMainCharacter().getHeroInventory().setAmountOfIron(user.getMainCharacter().getHeroInventory().getAmountOfIron() - upgradeButton.getUpgradeButtonItem().getAmountResToUpdate());
                    user.getMainCharacter().getHeroInventory().setAmountOfCoal(user.getMainCharacter().getHeroInventory().getAmountOfCoal() - upgradeButton.getUpgradeButtonItem().getAmountResToUpdate());
                    user.getMainCharacter().getHeroItems().get(0).setItemLvl(user.getMainCharacter().getHeroItems().get(0).getItemLvl() + 1);
                    upgradeButton.getUpgradeButtonItem().setItemLvl(user.getMainCharacter().getHeroItems().get(0).getItemLvl());
                    getActualResources();
                    upgradeButton.getNotification().close();
                    itemRepository.save(user.getMainCharacter().getHeroItems().get(0));
                }
                break;
            case POCKET:
                if (user.getMainCharacter().getHeroInventory().getAmountOfGold() >= upgradeButton.getUpgradeButtonItem().getAmountResToUpdate() && user.getMainCharacter().getHeroInventory().getAmountOfWool() >= upgradeButton.getUpgradeButtonItem().getAmountResToUpdate() && user.getMainCharacter().getHeroItems().get(1).getItemLvl() < 3) {
                    user.getMainCharacter().getHeroInventory().setAmountOfGold(user.getMainCharacter().getHeroInventory().getAmountOfGold() - upgradeButton.getUpgradeButtonItem().getAmountResToUpdate());
                    user.getMainCharacter().getHeroInventory().setAmountOfWool(user.getMainCharacter().getHeroInventory().getAmountOfWool() - upgradeButton.getUpgradeButtonItem().getAmountResToUpdate());
                    user.getMainCharacter().getHeroItems().get(1).setItemLvl(user.getMainCharacter().getHeroItems().get(1).getItemLvl() + 1);
                    upgradeButton.getUpgradeButtonItem().setItemLvl(user.getMainCharacter().getHeroItems().get(1).getItemLvl());
                    getActualResources();
                    upgradeButton.getNotification().close();
                    itemRepository.save(user.getMainCharacter().getHeroItems().get(1));
                }
                break;
            case BOOTS:
                if (user.getMainCharacter().getHeroInventory().getAmountOfSilver() >= upgradeButton.getUpgradeButtonItem().getAmountResToUpdate() && user.getMainCharacter().getHeroInventory().getAmountOfLeather() >= upgradeButton.getUpgradeButtonItem().getAmountResToUpdate() && user.getMainCharacter().getHeroItems().get(2).getItemLvl() < 3) {
                    user.getMainCharacter().getHeroInventory().setAmountOfSilver(user.getMainCharacter().getHeroInventory().getAmountOfSilver() - upgradeButton.getUpgradeButtonItem().getAmountResToUpdate());
                    user.getMainCharacter().getHeroInventory().setAmountOfLeather(user.getMainCharacter().getHeroInventory().getAmountOfLeather() - upgradeButton.getUpgradeButtonItem().getAmountResToUpdate());
                    user.getMainCharacter().getHeroItems().get(2).setItemLvl(user.getMainCharacter().getHeroItems().get(2).getItemLvl() + 1);
                    upgradeButton.getUpgradeButtonItem().setItemLvl(user.getMainCharacter().getHeroItems().get(2).getItemLvl());
                    getActualResources();
                    upgradeButton.getNotification().close();
                    itemRepository.save(user.getMainCharacter().getHeroItems().get(2));
                }
                break;
        }
    }

    private void initMissions() {
        listOfMissions = new ArrayList<>();
        listOfMissions.add(new Mission(1L, "Mission 1", 1, 300, 20, "Misja nr 1", "icons\\M1T.png", ""));
        listOfMissions.add(new Mission(2L, "Mission 2", 2, 480, 40, "Misja nr 2", "icons\\M2T.png", "icons\\M2L.png"));
        listOfMissions.add(new Mission(3L, "Mission 3", 3, 660, 60, "Misja nr 3", "icons\\M3T.png", "icons\\M3L.png"));
        listOfMissions.add(new Mission(4L, "Mission 4", 4, 840, 80, "Misja nr 4", "icons\\M4T.png", "icons\\M4L.png"));
        listOfMissions.add(new Mission(5L, "Mission 5", 5, 1020, 100, "Misja nr 5", "icons\\M5T.png", "icons\\M5L.png"));
        listOfMissions.add(new Mission(6L, "Mission 6", 6, 1200, 120, "Misja nr 6", "icons\\M6T.png", "icons\\M6L.png"));

        missions = new Tabs();
        for (Mission m : listOfMissions) {
            missions.add(m.getTab());
        }

        missionDetails = new Div();
        missionBackground = new Image(listOfMissions.get(0).getImageBackgroundURL(), "MissionImage");
        missionDetails.add(missionBackground);
        missionButton = new Button();
        missionButton.addClassName("mission-button");
        missionTabListener();

        missions.addSelectedChangeListener(selectedChangeEvent -> missionTabListener());
        missionButton.addClickListener(buttonClickEvent -> missionButtonListener());

        missionNotification = new MissionNotification();
    }

    private void missionButtonListener() {
        if (user.getMainCharacter().isOnMission() && user.getMainCharacter().getTimeToEndMission() == 0L && missionButton.getText().equals("Mission Completed!")) {
            missionNotification.showNotification(user.getMainCharacter().getAwards(actualMissionId, actualMissionExp));
            missions.setSelectedTab(listOfMissions.get((int) (actualMissionId - 1)).getTab());
            missionTabListener();
            getActualResources();
        } else {
            user.getMainCharacter().setOnMission(true);
            user.getMainCharacter().setHeroMissionId(actualMissionId);
            user.getMainCharacter().setTimeToEndMission(actualMissionTime - (long) (user.getMainCharacter().getHeroItems().get(2).getItemLvl() * user.getMainCharacter().getHeroItems().get(2).getItemLvl() * 0.1 * actualMissionTime));
            setMissionButtonOptions(true, user.getMainCharacter().getTimeToEndMission() + " sek to end mission", false);
        }
    }

    private void missionTabListener() {
        for (Mission m : listOfMissions) {
            if (m.getTab().isSelected()) {
                if (user.getMainCharacter().getHeroLvl() >= m.getMissionReqLvl()) {
                    actualMissionId = m.getMissionId();
                    actualMissionTime = m.getMissionTime();
                    actualMissionExp = m.getMissionExp();
                    missionBackground.setSrc(m.getImageBackgroundURL());
                    if (user.getMainCharacter().isOnMission() && user.getMainCharacter().getHeroMissionId().longValue() != m.getMissionId().longValue()) {
                        setMissionButtonOptions(false, "You're on the different mission", false);
                    } else if (user.getMainCharacter().isOnMission() && user.getMainCharacter().getHeroMissionId().longValue() == m.getMissionId().longValue() && user.getMainCharacter().getTimeToEndMission() != 0L) {
                        setMissionButtonOptions(true, user.getMainCharacter().getTimeToEndMission() + " sek to end mission", false);
                    } else if (user.getMainCharacter().isOnMission() && user.getMainCharacter().getHeroMissionId().longValue() == m.getMissionId().longValue() && user.getMainCharacter().getTimeToEndMission() == 0L) {
                        setMissionButtonOptions(false, "Mission Completed!", true);
                    } else {
                        setMissionButtonOptions(false, "Start Mission: " + (m.getMissionTime() / 60) + " min", true);
                    }
                } else if (user.getMainCharacter().getHeroLvl() < m.getMissionReqLvl()) {
                    missionBackground.setSrc(m.getBlockBackgroundURL());
                    setMissionButtonOptions(false, "Requed: " + m.getMissionReqLvl() + " level", false);
                }
            }
        }
    }

    private void setMissionButtonOptions(boolean isVisible, String text, boolean isEnabled) {
        isVisibleMissionButton = isVisible;
        missionButton.setText(text);
        missionButton.setEnabled(isEnabled);
    }

    private void setPositionOfComponents() {
        navBar.setPositionOfComponents(horizontalLayoutForResources, horizontalLayoutForExp);
        verticalLayoutForUpgButtons.add(listOfUpgradeButtons.get(0).getUpgradeButton(), listOfUpgradeButtons.get(1).getUpgradeButton(), listOfUpgradeButtons.get(2).getUpgradeButton());
        horizontalLayoutForMissions.add(missions, missionButton);
        verticalLayoutForMissions.add(horizontalLayoutForMissions, missionDetails);
        horizontalLayoutForAvatar.add(imageAvatar, verticalLayoutForUpgButtons, verticalLayoutForMissions);
        this.add(horizontalLayoutForResources, horizontalLayoutForExp, horizontalLayoutForAvatar);
    }

    private void getActualResources() {
        navBar.getActualResources(user);
        inventoryRepository.save(user.getMainCharacter().getHeroInventory());
        characterRepository.save(user.getMainCharacter());
    }

    private User getUser(String username) {
        return userRepository.findByUserName(username);
    }

    private Character getHero(String username) {
        return characterRepository.findByHeroId(getUser(username).getUserId());
    }

    private Inventory getInventory(String username) {
        return inventoryRepository.findByInventoryId(getUser(username).getUserId());
    }

    private List<Item> getItemList(String username) {
        return itemRepository.getItemsById(getUser(username).getUserId());
    }
}