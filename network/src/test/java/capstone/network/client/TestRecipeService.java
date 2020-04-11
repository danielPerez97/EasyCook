package capstone.network.client;

import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import capstone.network.client.testdi.DaggerTestNetworkComponent;

public class TestRecipeService
{
    @Inject RecipeService service;

    TestRecipeService()
    {
        DaggerTestNetworkComponent.create().inject(this);
        Objects.nonNull(service);
    }

    @Test
    void testAllRecipes()
    {
        List<Recipe> allRecipes = service.getAllRecipes().blockingFirst();
        assertTrue(allRecipes.size() > 0);
    }

}
