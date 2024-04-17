package com.vn.demo.listener;

import com.vn.demo.helper.LogHelper;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InsertEventListenerClass implements PostInsertEventListener {
    private final LogHelper logHelper;
    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        Object idObject =postInsertEvent.getId();
        logHelper.setId((Long) idObject);
    }


    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}
