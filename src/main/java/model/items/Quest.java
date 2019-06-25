package model.items;

public class Quest {

    private int id;
    private String name;
    private String discription;
    private String category;
    private int reward;

    public Quest(int id, String name, String discription, String category, int reward) {
        this.id = id;
        this.name = name;
        this.discription = discription;
        this.category = category;
        this.reward = reward;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getReward() {
        return reward;
    }

    public String getDiscription() {
        return discription;
    }
}
