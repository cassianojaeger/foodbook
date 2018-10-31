package br.ufrgs.foodbook.model.recipe;

import br.ufrgs.foodbook.model.enums.TimeType;
import br.ufrgs.foodbook.model.security.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "RECIPE_FEEDBACK", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "RECIPE_ID"})})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class RecipeFeedback
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="RECIPE_ID", nullable=false)
    private Recipe recipe;

    @Column(name = "COOK_DIFFICULTY")
    private Integer cookDifficulty;

    @ElementCollection
    @MapKeyColumn(name = "TIME_TYPE")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<TimeType, Double> cookTime;

    @Column(name = "COOK_TASTYNESS")
    private Integer cookTastyness;
}
