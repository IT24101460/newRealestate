import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
    private List<Integer> propertyIds; // List of property IDs owned by this seller

    public Seller(int userId, String username, String password, String name, String email, String phone) {
        super(userId, username, password, "SELLER", name, email, phone);
        this.propertyIds = new ArrayList<>();
    }

    // Property management
    public void addProperty(int propertyId) {
        propertyIds.add(propertyId);
    }

    public void removeProperty(int propertyId) {
        propertyIds.remove(Integer.valueOf(propertyId));
    }

    public List<Integer> getPropertyIds() {
        return new ArrayList<>(propertyIds);
    }

    public void setPropertyIds(List<Integer> propertyIds) {
        this.propertyIds = new ArrayList<>(propertyIds);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "userId=" + getUserId() +
                ", username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", propertyIds=" + propertyIds +
                '}';
    }
} 