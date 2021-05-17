package kz.zhelezyaka.services;

import kz.zhelezyaka.commands.RecipeCommand;
import kz.zhelezyaka.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(String id);

    void deleteById(String idToDelete);
}
