package com.sentinelauth.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User Entity - Represents a user in the SentinelAuth system.
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_users_username", columnList = "username", unique = true),
    @Index(name = "idx_users_email", columnList = "email", unique = true),
    @Index(name = "idx_users_is_active", columnList = "is_active"),
    @Index(name = "idx_users_is_locked", columnList = "is_locked"),
    @Index(name = "idx_users_created_at", columnList = "created_at"),
    @Index(name = "idx_users_last_login_timestamp", columnList = "last_login_timestamp")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Column(nullable = false, unique = true, length = 255)
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank(message = "Password hash cannot be blank")
    @Column(nullable = false, length = 500)
    private String passwordHash;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked = false;

    @Column(name = "failed_login_attempts", nullable = false)
    private Integer failedLoginAttempts = 0;

    @Column(name = "last_login_timestamp")
    private LocalDateTime lastLoginTimestamp;

    @Column(name = "password_change_timestamp")
    private LocalDateTime passwordChangeTimestamp;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "updated_by", length = 255)
    private String updatedBy;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false),
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        if (this.roles != null) {
            this.roles.remove(role);
        }
    }
}