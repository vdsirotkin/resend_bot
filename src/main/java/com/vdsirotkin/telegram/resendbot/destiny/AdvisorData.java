package com.vdsirotkin.telegram.resendbot.destiny;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitalijsirotkin on 22.11.16.
 */
public class AdvisorData implements Serializable {
    private Strike nightfall;
    @SerializedName("siva_heroic")
    private Strike sivaHeroic;
    @SerializedName("weekly_crucible")
    private String weeklyCrucible;
    @SerializedName("weekly_raid")
    private Strike weeklyRaid;


    public static class Strike {
        private String name;
        private List<String> modifiers;

        public String getName() {
            return name;
        }

        public Strike setName(String name) {
            this.name = name;
            return this;
        }

        public List<String> getModifiers() {
            if (modifiers == null) {
                modifiers = new ArrayList<>();
            }
            return modifiers;
        }
    }

    public Strike getNightfall() {
        if (nightfall == null) {
            nightfall = new Strike();
        }
        return nightfall;
    }

    public Strike getSivaHeroic() {
        if (sivaHeroic == null) {
            sivaHeroic = new Strike();
        }
        return sivaHeroic;
    }

    public Strike getWeeklyRaid() {
        if (weeklyRaid == null) {
            weeklyRaid = new Strike();
        }
        return weeklyRaid;
    }

    public String getWeeklyCrucible() {
        return weeklyCrucible;
    }

    public AdvisorData setWeeklyCrucible(String weeklyCrucible) {
        this.weeklyCrucible = weeklyCrucible;
        return this;
    }


}
