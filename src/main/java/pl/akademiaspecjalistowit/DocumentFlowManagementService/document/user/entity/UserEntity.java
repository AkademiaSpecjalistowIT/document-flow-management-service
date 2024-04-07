package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String name;
    private String surname;
    private String email;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public UserEntity(String username, String name, String surname,
                      String email, RoleEntity role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }
}
