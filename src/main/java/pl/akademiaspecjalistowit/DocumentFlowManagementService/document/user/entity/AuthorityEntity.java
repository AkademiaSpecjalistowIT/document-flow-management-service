package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "authorities")
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String value;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public AuthorityEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
