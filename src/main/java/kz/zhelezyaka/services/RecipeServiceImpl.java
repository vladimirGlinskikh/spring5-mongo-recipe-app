package kz.zhelezyaka.services;

import kz.zhelezyaka.commands.RecipeCommand;
import kz.zhelezyaka.converters.RecipeCommandToRecipe;
import kz.zhelezyaka.converters.RecipeToRecipeCommand;
import kz.zhelezyaka.domain.Recipe;
import kz.zhelezyaka.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                    recipeCommand.getIngredients().forEach(rc -> {
                        rc.setRecipeId(recipeCommand.getId());
                    });
                    return recipeCommand;
                });
    }

    @Override
    public void deleteById(String idToDelete) {
        recipeReactiveRepository.deleteById(idToDelete).block();
    }
}
