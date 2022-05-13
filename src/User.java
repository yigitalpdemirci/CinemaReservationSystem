
public class User{
    private String name;
    private String password;
    private boolean isClubMember;
    private boolean isAdmin;

    public User(String name, String password, boolean isClubMember, boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.isClubMember = isClubMember;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsClubMember() {
        return isClubMember;
    }

    public void setIsClubMember(boolean clubMember) {
        isClubMember = clubMember;
    }

    @Override
    public String toString() {
        return name;
    }
}
