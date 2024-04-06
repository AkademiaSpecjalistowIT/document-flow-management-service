package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserEntity> users = new HashSet<>();
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuthorityEntity> authorities;

    public RoleEntity(String roleName, Set<UserEntity> users, Set<AuthorityEntity> authorities) {
        this.roleName = roleName;
        this.users = users;
        this.authorities = authorities;
    }
}
