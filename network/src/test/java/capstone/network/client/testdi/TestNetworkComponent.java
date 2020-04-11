package capstone.network.client.testdi;

import javax.inject.Singleton;

import capstone.network.client.NetworkModule;
import capstone.network.client.TestRecipeService;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface TestNetworkComponent
{
    void inject(TestRecipeService service);
}
