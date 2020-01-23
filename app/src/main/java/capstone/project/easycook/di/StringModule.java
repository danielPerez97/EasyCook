// Dummy dependency injector, this will serve as an example to copy from

package capstone.project.easycook.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StringModule
{
    @Provides
    @Singleton
    public String provideString()
    {
        return "test message";
    }
}
