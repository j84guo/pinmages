import services.PinmagesService;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(PinmagesService.class).asEagerSingleton();
    }
}