package com.vn.demo.listener;

import com.vn.demo.helper.LogHelper;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UpdateEventListenerClass implements PostUpdateEventListener {

    private final LogHelper logHelper;

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        String[] properties = event.getPersister().getPropertyNames();
        Object[] oldState = event.getOldState();
        Object[] newState = event.getState();
        Object idObject = event.getId();
        logHelper.setId((Long) idObject);
        Map<String, List<Object>> diffField = combineArraysToMap(properties, oldState, newState);
        logHelper.setDifferenceField(diffField);

    }

    public static Map<String, List<Object>> combineArraysToMap(Object[] arr1, Object[] arr2, Object[] arr3) {
        Map<String, List<Object>> resultMap = new HashMap<>();

        for (int i = 0; i < arr1.length; i++) {
            String key = (String) arr1[i];
            Object oldValue = arr2[i];
            Object newValue = arr3[i];

            if (!Objects.equals(oldValue, newValue)) { // So sánh giá trị
                List<Object> values = new ArrayList<>();
                values.add(oldValue);
                values.add(newValue);
                resultMap.put(key, values);
            }
        }

        return resultMap;
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}