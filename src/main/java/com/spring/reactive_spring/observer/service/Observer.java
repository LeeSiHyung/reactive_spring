package com.spring.reactive_spring.observer.service;

public interface Observer <T>{
    void observe(T event);
}
