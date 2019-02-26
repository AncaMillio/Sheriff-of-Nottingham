package main;


public final class Bunuri {
    int id;
    String asset;
    String type;      //ilegal sau legal
    int profit;
    int penalty;
    private int bonus;     //adus de produsele ilegale
    private final int three = 3;
    private final int four = 4;
    private final int ten = 10;
    private final int seven = 7;
    private final int eight = 8;
    private final int nine = 9;
    private final int eleven = 11;
    private final int twelve = 12;

    void setBonus(final int i) {
        bonus = i;
    }

    int getBonus() {
        return bonus;
    }

    Bunuri(final int id) {
        switch (id) {

        case 0:
            this.id = 0;
            asset = "Apple";
            type = "Legal";
            profit = 2;
            penalty = 2;
            setBonus(0);
            break;

        case 1:
            this.id = 1;
            asset = "Cheese";
            type = "Legal";
            profit = three;
            penalty = 2;
            setBonus(0);
            break;

        case 2:
            this.id = 2;
            asset = "Bread";
            type = "Legal";
            profit = four;
            penalty = 2;
            setBonus(0);
            break;

        case three:
            this.id = three;
            asset = "Chicken";
            type = "Legal";
            profit = four;
            penalty = 2;
            setBonus(0);
            break;

        case ten:
            this.id = ten;
            asset = "Silk";
            type = "Illegal";
            profit = nine;
            penalty = four;
            setBonus(nine);
            break;

        case eleven:
            this.id = eleven;
            asset = "Pepper";
            type = "Illegal";
            profit = eight;
            penalty = four;
            setBonus(eight);
            break;

        case twelve:
            this.id = twelve;
            asset = "Barrel";
            type = "Illegal";
            profit = seven;
            penalty = four;
            setBonus(eight);
            break;

        default:
            break;
        }
    }
}
