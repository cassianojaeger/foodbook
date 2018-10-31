package br.ufrgs.foodbook.model.recipe;

import br.ufrgs.foodbook.model.enums.TimeType;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "RECIPE")
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
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PHOTO")
    private String photo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "PREPARE_STEPS")
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
    private Map<TimeType, Double> cookTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @OrderBy
    @JsonIgnore
    private List<RecipeFeedback> recipeFeedbacks;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String ingredients;

    @Column(name = "COOK_DIFFICULTY")
    private Integer cookDifficulty;
}
