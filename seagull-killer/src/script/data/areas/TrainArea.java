package script.data.areas;

import org.rspeer.runetek.api.movement.position.Area;

public enum TrainArea {
    NEAR_PUB(Area.rectangular(3021, 3244, 3039, 3227)),
    NEAR_FOOD_SHOP(Area.rectangular(3017, 3213, 3035, 3198)),
    SOUTH_DOCKS_1(Area.rectangular(3047, 3206, 3067, 3200)),
    SOUTH_DOCKS_2(Area.rectangular(3045, 3196, 3068, 3190));

    private Area area;

    TrainArea(Area trainArea) {
        this.area = trainArea;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
