package lunchVote.model;

public class Restaurant extends AbstractBaseEntity {
    private String name;
    private String address;

    public Restaurant(Integer id, String name, String address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.name, restaurant.address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
