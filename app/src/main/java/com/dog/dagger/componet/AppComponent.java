package com.dog.dagger.componet;

import com.dog.dagger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cuiyue on 2017/1/24.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

}
