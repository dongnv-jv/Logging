package com.vn.demo.listener;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HibernateListener {

    private final EntityManagerFactory entityManagerFactory;
    private final InsertEventListenerClass insertEventListenerClass;
    private final UpdateEventListenerClass updateEventListenerClass;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        assert registry != null;
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(insertEventListenerClass);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(updateEventListenerClass);
    }
}
