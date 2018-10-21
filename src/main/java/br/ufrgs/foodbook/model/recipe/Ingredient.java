package br.ufrgs.foodbook.model.recipe;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "INGREDIENT", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Ingredient
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;
}
