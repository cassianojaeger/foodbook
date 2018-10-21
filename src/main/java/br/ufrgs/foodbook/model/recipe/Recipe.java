package br.ufrgs.foodbook.model.recipe;

import br.ufrgs.foodbook.model.enums.TimeType;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.security.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "RECIPE", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Recipe
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Lob
    @Column(name = "DESCRIPTION", columnDefinition = "CLOB")
    private String description;

    @Column(name = "PHOTO")
    private String photo;

    @Lob
    @Column(name = "PREPARE_STEPS", columnDefinition = "CLOB")
    private String prepareSteps;

    @ManyToOne
    @JoinColumn(name="CREATOR_ID", nullable=false)
    private User creator;

    @ManyToOne
    @JoinColumn(name="GROUP_ID", nullable=false)
    private Group group;

    @ElementCollection
    @MapKeyColumn(name = "TIME_TYPE")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<TimeType, Integer> cookTime;

    @ManyToMany
    @JoinTable(
            name="RECIPE_INGREDIENTS",
            joinColumns={@JoinColumn(name="recipe_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="ingredient_id", referencedColumnName="id")})
    @MapKeyColumn(name = "MEASURE")
    private Map<String, Ingredient> ingredients;

    @Column(name = "COOK_DIFFICULTY")
    private Double cookDifficulty;
}
