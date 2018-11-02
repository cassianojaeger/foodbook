package br.ufrgs.foodbook.model.security;

import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER_", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME" }) })
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ACCOUNT_EXPIRED")
    private boolean accountExpired;

    @Column(name = "ACCOUNT_LOCKED")
    private boolean accountLocked;

    @Column(name = "CREDENTIALS_EXPIRED")
    private boolean credentialsExpired;

    @Column(name = "ENABLED")
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "administrator", orphanRemoval = true)
    @OrderBy
    @JsonIgnore
    private Set<Group> managedGroups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator", orphanRemoval = true)
    @OrderBy
    @JsonIgnore
    private Set<Recipe> ownedRecipes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @OrderBy
    @JsonIgnore
    private Set<RecipeFeedback> recipesFeedbacks;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_AUTHORITIES",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")
    )
    @OrderBy
    @JsonIgnore
    private Set<Authority> authorities;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "FAVORITE_RECIPES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID")
    )
    @OrderBy
    @JsonIgnore
    private Set<Recipe> favoriteRecipes;

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }
}