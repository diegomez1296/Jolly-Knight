package pl.rydzinski.spring.GameComponents;

import com.vaadin.flow.component.tabs.Tab;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mission {
    private Long missionId;
    private String missionTitle;
    private int missionReqLvl;
    private long missionTime;
    private int missionExp;
    private String missionDesc;
    private Tab tab;
    private String imageBackgroundURL;
    private String blockBackgroundURL;

    public Mission(Long missionId, String missionTitle, int missionReqLvl, long missionTime, int missionExp, String missionDesc, String imageBackgroundURL, String blockBackgroundURL) {
        this.missionId = missionId;
        this.missionTitle = missionTitle;
        this.missionReqLvl = missionReqLvl;
        this.missionTime = missionTime;
        this.missionExp = missionExp;
        this.missionDesc = missionDesc;
        this.tab = new Tab(missionTitle);
        this.tab.setClassName("MissionTab");
        this.imageBackgroundURL = imageBackgroundURL;
        this.blockBackgroundURL = blockBackgroundURL;
    }
}
