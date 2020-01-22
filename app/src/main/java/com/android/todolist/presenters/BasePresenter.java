package com.android.todolist.presenters;

public abstract class BasePresenter<V> {
    public abstract void attachView(V view);

    public abstract void detachView();
}
