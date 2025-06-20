package com.jnzydzx.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries(
        @NamedQuery(
                name = "getAllUserInformation",
                query = "SELECT u.logicId, u.userName, u.userId, u.passWord FROM User u"
        )
)
public class User {
    @Id
    @Column(name = "lid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logicId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_pwd", nullable = false)
    private String passWord;

    public void copy(User user) {
        this.userName = user.getUserName();
        this.userId = user.getUserId();
        this.passWord = user.getPassWord();
    }
}
