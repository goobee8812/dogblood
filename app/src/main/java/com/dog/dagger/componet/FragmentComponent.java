package com.dog.dagger.componet;

import android.app.Activity;


import com.dog.dagger.module.FragmentModule;
import com.dog.dagger.scope.FragmentScope;
import com.dog.ui.fragment.NewsFragment;
import com.dog.ui.fragment.NewsTabFragment;
import com.dog.ui.fragment.VideoFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(NewsFragment newsFragment);

    void inject(NewsTabFragment newsTabFragment);

    void inject(VideoFragment videoFragment);

}
