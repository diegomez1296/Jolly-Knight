package pl.rydzinski.spring.GUI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.rydzinski.spring.Repositories.UserRepository;
import pl.rydzinski.spring.UserPackage.User;

import java.util.ArrayList;
import java.util.List;

@Route(value = "")
@StyleSheet("frontend://styles/styleLoginGUI.css")
public class LoginGUI extends VerticalLayout {

    private final H2 TITLE = new H2("");

    private VerticalLayout verticalLayoutTextFields;
    private VerticalLayout verticalLayoutAvatar;
    private HorizontalLayout horizontalLayout;

    private TextField textFieldNickname;
    private TextField textFieldPassword;
    private TextField textFieldEmail;

    private Image heroAvatar;
    private ComboBox<String> heroAvatarURL;

    private Button buttonPlay;

    private static List<String> userData;

    @Autowired
    private UserRepository userRepository;

    public LoginGUI() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        addClassName("login-gui");
        initComponents();
    }

    private void initComponents() {

        verticalLayoutTextFields = new VerticalLayout();
        verticalLayoutAvatar = new VerticalLayout();
        horizontalLayout = new HorizontalLayout();

        textFieldNickname = new TextField("Nick:");
        textFieldPassword = new TextField("Password:");
        textFieldEmail = new TextField("Email:");

        textFieldNickname.setValue("Nick");
        textFieldPassword.setValue("Password");
        textFieldEmail.setValue("example@email.com");

        heroAvatar = new Image("icons\\KnightRed.jpg", "Knight");
        heroAvatar.addClassName("responsiveHeroImage");
        heroAvatar.setHeight("370px");
        heroAvatar.setWidth("192px");

        heroAvatarURL = new ComboBox<>("Avatar");
        //heroAvatarURL.getElement().setAttribute("theme", "dark");
        heroAvatarURL.setItems("KnightRed", "KnightBlue", "KnightYellow", "KnightGirl");
        heroAvatarURL.setValue("KnightRed");
        heroAvatarURL.addValueChangeListener(event -> {
            try {
                setImage(event.getValue());
            } catch (Exception e) {
            }
        });

        buttonPlay = new Button("Play!");
        buttonPlay.setWidth("100%");
        buttonPlay.addClickListener(buttonClickEvent -> buttonPlayListener());

        verticalLayoutTextFields.add(textFieldNickname, textFieldPassword, textFieldEmail, buttonPlay);
        verticalLayoutAvatar.add(heroAvatar, heroAvatarURL);
        horizontalLayout.add(verticalLayoutTextFields, verticalLayoutAvatar);
        horizontalLayout.getElement().setAttribute("theme", "dark");

        this.add(TITLE, horizontalLayout);
    }

    private void setImage(String knightName) {
        switch (knightName) {
            case "KnightRed":
                heroAvatar.setSrc("icons\\KnightRed.jpg");
                break;
            case "KnightBlue":
                heroAvatar.setSrc("icons\\KnightBlue.jpg");
                break;
            case "KnightYellow":
                heroAvatar.setSrc("icons\\KnightYellow.jpg");
                break;
            case "KnightGirl":
                heroAvatar.setSrc("icons\\KnightGirl.jpg");
                break;
            default:
                break;
        }
    }

    private void buttonPlayListener() {

        userData = new ArrayList<>();
        userData.add(textFieldNickname.getValue());
        userData.add(textFieldPassword.getValue());
        userData.add(textFieldEmail.getValue());
        userData.add(heroAvatar.getSrc());

        try {
            User user = userRepository.findByUserName(textFieldNickname.getValue());

            if(user.getUserPasswd().equals(textFieldPassword.getValue())) {
                buttonPlay.getUI().ifPresent(ui -> ui.navigate("game"));
            } else {
                Notification notification = new Notification("Wrong password!");
                notification.setDuration(3000);
                notification.open();
            }
        } catch (Exception e) {
            buttonPlay.getUI().ifPresent(ui -> ui.navigate("game"));
        }



    }

    public List<String> getUserData() {
        return userData;
    }
}
