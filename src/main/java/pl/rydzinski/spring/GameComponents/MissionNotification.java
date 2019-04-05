package pl.rydzinski.spring.GameComponents;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import lombok.Data;

import java.util.List;

@Data
public class MissionNotification {

    private H4 notificationTitle;
    private H5 notificationContentExp;
    private H5 notificationContentAmountOfItems;
    private H5 notificationContentListOfItems;
    private NativeButton notificationButton;
    private Notification notification;

    public MissionNotification() {
        notificationTitle = new H4("!Mission Accomplished!");
        notificationTitle.addClassName("notif-hX");
        notificationContentExp = new H5();
        notificationContentExp.addClassName("notif-hX");
        notificationContentAmountOfItems = new H5();
        notificationContentAmountOfItems.addClassName("notif-hX");
        notificationContentListOfItems = new H5();
        notificationContentListOfItems.addClassName("notif-hX");
        notificationButton = new NativeButton("Close");
        notification = new Notification(notificationTitle, notificationContentExp, notificationContentAmountOfItems, notificationContentListOfItems, notificationButton);
        notification.setDuration(15000);
        notificationButton.addClickListener(event -> notification.close());
        notification.setPosition(Notification.Position.MIDDLE);
        notificationButton.addClassName("notif-button");
    }

    public void showNotification(List<String> content) {
        notificationContentExp.setText(content.get(0));
        notificationContentAmountOfItems.setText(content.get(1));
        notificationContentListOfItems.setText(content.get(2));
        notification.open();
    }
}
