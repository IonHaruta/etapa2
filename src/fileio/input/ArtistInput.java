package fileio.input;

public final class ArtistInput {
    private String username;
    private int age;
    private String city;
    private String type;

    public ArtistInput() {
    }

    @Override
    public String toString() {
        return "ArtistInput{"
                + "username='"
                + username
                + '\''
                + ", age="
                + age
                + ", city='"
                + city
                + '\''
                + ", type='"
                + type
                + '\''
                + '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
