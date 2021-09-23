package ru.ac.uniyar.testingcourse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeTest {
    /**
     * проверяет добавление в не полный список
     * проверяет, что первый элемент тот, который вставили
     */
    @Test
    void OfferFirstNotFullDeque(){
        LinkedBlockingDeque deque = new LinkedBlockingDeque(5);
        for(int i=0; i<4; i++)
            deque.add(i);
        assertThat(deque.offerFirst(6)).isTrue();
        assertThat(deque.getFirst()).isEqualTo(6);
    }

    /**
     * проверяет добавление в полный список
     */
    @Test
    void OfferFirstFullDeque(){
        LinkedBlockingDeque deque = new LinkedBlockingDeque(5);
        for(int i=0; i<5; i++)
            deque.add(i);
        assertThat(deque.offerFirst(6)).isFalse();
    }

    /**
     * проверяет перенос списка в массив
     */
    @Test
    void drainToNormalArray(){
        LinkedBlockingDeque deque = new LinkedBlockingDeque(5);
        ArrayList<Integer> check = new ArrayList();
        ArrayList<Integer> arrayList = new ArrayList();
        for(int i=0; i<5; i++) {
            deque.add(i);
            check.add(i);
        }
        deque.drainTo(arrayList);
        assertThat(check.equals(arrayList)).isTrue();
    }

    /**
     * проверяет выбрасывание исключения
     */
    @Test
    void drainToHimself(){
        LinkedBlockingDeque deque = new LinkedBlockingDeque(5);
        for(int i=0; i<5; i++) {
            deque.add(i);
        }
        assertThatThrownBy(() -> deque.drainTo(deque)).isInstanceOf(IllegalArgumentException.class);
    }
}
