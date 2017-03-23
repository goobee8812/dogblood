package com.dog.dagger.componet;

import android.app.Activity;


import com.dog.dagger.module.ActivityModule;
import com.dog.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

}
