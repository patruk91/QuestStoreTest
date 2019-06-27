package model.items;

public class Artifact {
    private int id;
    private String name;
    private String discription;
    private String category;
    private int price;
    private boolean avaliability;

    public Artifact(int id, String name, String discription, String category, int price, boolean avaliability) {
        this.id = id;
        this.name = name;
        this.discription = discription;
        this.category = category;
        this.price = price;
        this.avaliability = avaliability;
    }

    public Artifact(int id, String category, int price, boolean avaliability) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.avaliability = avaliability;
    }

    public Artifact(int id, String name, String category, int price, String discription){
        this.id = id;
        this.name = name;
        this.discription = discription;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDiscription() {
        return discription;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvaliability() {
        return avaliability;
    }
}
