package pl.wb.security.jpa;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


@Entity
@Table(name = "user")
@NamedQuery(name = User.FIND_ALL_USERS, query = "select u from User u order by u.id")
@NamedQuery(name = User.FIND_USER_BY_ID, query = "select u from User u where u.id =:id")
@NamedQuery(name = User.FIND_USER_BY_EMAIL, query = "select u from User u where u.email =:email")
@NamedQuery(name = User.FIND_USER_BY_PASSWORD, query = "select u from User u where u.password =:password")
public class User extends AbstractEntity {

    public static final String FIND_ALL_USERS = "User.findAllUsers";
    public static final String FIND_USER_BY_ID = "User.findById";
    public static final String FIND_USER_BY_EMAIL = "User.findByEmail";
    public static final String FIND_USER_BY_PASSWORD = "User.findByPassword";


    @NotNull(message = "Full name must be set")
    @Column(name = "username")
    private String userName;

    @NotNull(message = "Password must be set")
    @Size(min = 8, message = "Password must have minimum size of 8 characters")
    @Column(name = "password")
    private String password;

    @NotNull(message = "First name must be set")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last name must be set")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Email must be set")
    @Email(message = "email must be in proper form!")
    @Column(name = "email")
    private String email;

    @Column(name = "salt")
    private String salt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private final Collection<Game> gamesCollection = new ArrayList<>();

    public User() {
    }

    public User(String userName, String password, String firstName, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gamesCollection=" + gamesCollection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userName.equals(user.userName) &&
                password.equals(user.password) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, firstName, lastName, email);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Game> getGamesCollection() {
        return gamesCollection;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}


//
//  https://thorben-janssen.com/hibernate-best-practices-for-readable-and-maintainable-code
//
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//@Table(name = "user")
//@NamedEntityGraph(name = User.GRAPH_USER_DATA,
//        attributeNodes = @NamedAttributeNode(value = "emails", subgraph = "emailsGraph"),
//        subgraphs = @NamedSubgraph(name = "emailsGraph", attributeNodes = @NamedAttributeNode("user")))
//@NamedQuery(name = User.QUERY_ALL, query = "SELECT u FROM User u")
//public class User extends AbstractEntity {
//
//    public static final String GRAPH_USER_DATA = "graph_user_data";
//
//    public static final String QUERY_ALL = "query.User.all";
//
//    @Column(name = "username")
//    public static final String ATTRIBUTE_USERNAME = "userName";
//
//    @Column(name = "first_name")
//    public static final String ATTRIBUTE_PASSWORD = "password";
//
//    @Column(name = "last_name")
//    public static final String ATTRIBUTE_FIRSTNAME = "firstName";
//
//    @Column(name = "email")
//    public static final String ATTRIBUTE_LASTNAME = "lastName";
//
//    @Column(name = "email")
//    public static final String ATTRIBUTE_EMAIL = "emails";

