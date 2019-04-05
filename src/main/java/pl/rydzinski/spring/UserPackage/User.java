package pl.rydzinski.spring.UserPackage;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.rydzinski.spring.HeroPackage.Character;

import javax.persistence.*;


@Data
@NoArgsConstructor

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    private String userPasswd;
    private String userEmail;
    @OneToOne
    private Character mainCharacter;

    public User(String userName, String userPasswd, String userEmail, Character mainCharacter) {
        this.userName = userName;
        this.userPasswd = userPasswd;
        this.userEmail = userEmail;

        this.mainCharacter = mainCharacter;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPasswd='" + "****" + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
