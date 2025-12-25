package com.sentinelauth.model.entity;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Role Entity
 * Represents a role in the authentication system with associated permissions.
 * Implements many-to-many relationship with Permission entity.
 */
@Entity
@Table(
    name = "roles",
    indexes = {
        @Index(name = "idx_role_name", columnList = "name", unique = true),
        @Index(name = "idx_role_is_active", columnList = "is_active"),
        @Index(name = "idx_role_created_at", columnList = "created_at"),
        @Index(name = "idx_role_updated_at", columnList = "updated_at"),
        @Index(name = "idx_role_created_by", columnList = "created_by")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "permissions")
@ToString(exclude = "permissions")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the role
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Unique name of the role
     */
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    /**
     * Description of the role
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Flag indicating if the role is active
     */
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Timestamp when the role was created
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the role was last updated
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * User who created the role
     */
    @Column(name = "created_by", length = 100)
    private String createdBy;

    /**
     * User who last updated the role
     */
    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    /**
     * Many-to-many relationship with Permission entity
     */
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"),
        indexes = {
            @Index(name = "idx_role_permissions_role_id", columnList = "role_id"),
            @Index(name = "idx_role_permissions_permission_id", columnList = "permission_id")
        }
    )
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

    /**
     * JPA lifecycle callback - sets createdAt timestamp
     */
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * JPA lifecycle callback - updates updatedAt timestamp
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Helper method to add a permission to the role
     *
     * @param permission the permission to add
     */
    public void addPermission(Permission permission) {
        if (permission != null) {
            this.permissions.add(permission);
            // Maintain bidirectional relationship
            if (permission.getRoles() != null) {
                permission.getRoles().add(this);
            }
        }
    }

    /**
     * Helper method to remove a permission from the role
     *
     * @param permission the permission to remove
     */
    public void removePermission(Permission permission) {
        if (permission != null) {
            this.permissions.remove(permission);
            // Maintain bidirectional relationship
            if (permission.getRoles() != null) {
                permission.getRoles().remove(this);
            }
        }
    }

    /**
     * Clears all permissions from the role
     */
    public void clearPermissions() {
        if (this.permissions != null) {
            // Create a copy to avoid ConcurrentModificationException
            new HashSet<>(this.permissions).forEach(this::removePermission);
        }
    }

    /**
     * Check if the role has a specific permission
     *
     * @param permission the permission to check
     * @return true if the role has the permission, false otherwise
     */
    public boolean hasPermission(Permission permission) {
        return this.permissions != null && this.permissions.contains(permission);
    }

    /**
     * Get the number of permissions in the role
     *
     * @return the count of permissions
     */
    public int getPermissionCount() {
        return this.permissions != null ? this.permissions.size() : 0;
    }
}
