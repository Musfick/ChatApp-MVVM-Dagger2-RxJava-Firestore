package com.foxhole.chatappmvvmdagger2rxjavafirestore.di;

import android.app.Application;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.BaseActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        AppModule.class,
        ViewModelFactoryModule.class
})
public interface AppComponent extends AndroidInjector<BaseActivity> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
