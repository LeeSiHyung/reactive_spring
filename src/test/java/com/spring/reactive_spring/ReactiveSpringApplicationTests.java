package com.spring.reactive_spring;

import com.spring.reactive_spring.observer.service.Observer;
import com.spring.reactive_spring.observer.service.Subject;
import com.spring.reactive_spring.observer.service.impl.ConcreteObserverA;
import com.spring.reactive_spring.observer.service.impl.ConcreteObserverB;
import com.spring.reactive_spring.observer.service.impl.ConcreteSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveSpringApplicationTests {

    @Test
    public void observersHandleEventsFromSubject() {
        // given
        Subject<String> subject = new ConcreteSubject();
        Observer<String> observerA = Mockito.spy(new ConcreteObserverA());
        Observer<String> observerB = Mockito.spy(new ConcreteObserverB());

        // when
        subject.notifyObservers("No Listeners");

        subject.registerObserver(observerA);
        subject.notifyObservers("Message for A");

        subject.registerObserver(observerB);
        // observerA, observerB 모두에게 전달
        subject.notifyObservers("Message for A & B");

        // observerA 삭제하기 때문에 B만 호출된다.
        subject.unregisterObserver(observerA);
        subject.notifyObservers("Message for B");

        subject.unregisterObserver(observerB);
        subject.notifyObservers("No listeners");

        // then
        Mockito.verify(observerA, times(1)).observe("Message for A");
        Mockito.verify(observerA, times(1)).observe("Message for A & B");
        // verifyNoMoreInteractions()는 더 이상의 메소드 호출이 없음을 체크
        Mockito.verifyNoMoreInteractions(observerA);

        Mockito.verify(observerB, times(1)).observe("Message for A & B");
        Mockito.verify(observerB, times(1)).observe("Message for B");
        Mockito.verifyNoMoreInteractions(observerB);
    }

    @Test
    public void subjectLeveragesLambdas(){
        Subject<String> subject = new ConcreteSubject();

        // 람다식은 (인터페이스 한개) 함수를 구현한 객체가 된다. 위 예제에서 new ConcreteObserverA() 혹은 익명 클래스
        subject.registerObserver(e -> System.out.println("A : " + e));
        subject.registerObserver(e -> System.out.println("B : " + e));

        subject.notifyObservers("This Message will receive A & B");

    }

}
