package com.spring.reactive_spring.observer.service.impl;

import com.spring.reactive_spring.observer.service.Observer;
import com.spring.reactive_spring.observer.service.Subject;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcreteSubject implements Subject<String> {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Set<Observer<String>> observers = new CopyOnWriteArraySet<>();

    @Override
    public void registerObserver(Observer<String> observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer<String> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        // observers.forEach(observer -> observer.observe(event));
        observers.forEach(observer -> executorService.submit(() -> observer.observe(event)));
    }
}
