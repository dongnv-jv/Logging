package com.vn.demo.listener;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InsertEventListenerClass implements PostInsertEventListener {
    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        //Subscriber to the insert events on your entities.
        System.out.println("The Event comes here with data: " + Arrays.toString(postInsertEvent.getState()));
    }


    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}
