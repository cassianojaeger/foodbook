package br.ufrgs.foodbook.model.groups;

import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "GROUP_")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Group
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group", orphanRemoval = true)
    @OrderBy
    @JsonIgnore
    private Set<Recipe> recipes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_MEMBERS",
        joinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    )
    @OrderBy
    @JsonIgnore
    private Set<User> members;

    @ManyToOne
    @JoinColumn(name="ADMIN_ID", nullable=false)
    private User administrator;
}
