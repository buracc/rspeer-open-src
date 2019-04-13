package script.util;

import org.rspeer.runetek.api.component.Shop;

public class PlantShop {

    public static boolean isEmpty = false;

    public static void setIsEmpty(boolean empty) {
        isEmpty = empty;
    }

    public static boolean shopHasBaggedPlants() {
        return Shop.getQuantity(8431) > 4;
    }
}
