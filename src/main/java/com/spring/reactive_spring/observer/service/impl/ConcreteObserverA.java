package com.spring.reactive_spring.observer.service.impl;

import com.spring.reactive_spring.observer.service.Observer;

public class ConcreteObserverA implements Observer<String> {

    @Override
    public void observe(String event) {
        System.out.println("Observer A : " + event);
    }

}
