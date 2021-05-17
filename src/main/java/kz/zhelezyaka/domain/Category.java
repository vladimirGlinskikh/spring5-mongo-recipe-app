package kz.zhelezyaka.domain;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipes"})
public class Category {

    private String id;
    private String description;
    private Set<Recipe> recipes;
}
