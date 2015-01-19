package com.pmrodrigues.gnsnet.events;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.String.format;

/**
 * Created by Marceloo on 14/11/2014.
 */
@Component
public class HibernateEventRegistry implements Integrator {

    public static Logger logging = Logger.getLogger(HibernateEventRegistry.class);


    @Override
    public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        final EventListenerRegistry eventRegistry = serviceRegistry.getService(EventListenerRegistry.class);

        eventRegistry.appendListeners(EventType.PRE_INSERT , new PreInsertEventListener() {
            @Override
            public boolean onPreInsert(PreInsertEvent event) {
                Object entity = event.getEntity();
                invoke(entity , PrePersist.class);
                return false;
            }

        });

        eventRegistry.appendListeners(EventType.PRE_UPDATE, new PreUpdateEventListener() {
            @Override
            public boolean onPreUpdate(PreUpdateEvent event) {
                Object entity = event.getEntity();
                invoke(entity , PreUpdate.class);
                return false;
            }
        });
    }

    private void invoke(Object entity , Class eventType) {
        for (final Method method : entity.getClass().getMethods()) { //NOPMD
            if (method.isAnnotationPresent(eventType)) {
                try {
                    logging.debug(format("executando o metodo %s da classe %s" , method.getName() , entity.getClass().getName()));
                    method.invoke(entity);
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        final EventListenerRegistry eventRegistry = serviceRegistry.getService(EventListenerRegistry.class);

        eventRegistry.appendListeners(EventType.PRE_INSERT , new PreInsertEventListener() {
            @Override
            public boolean onPreInsert(PreInsertEvent event) {
                Object entity = event.getEntity();
                invoke(entity , PrePersist.class);
                return false;
            }

        });

        eventRegistry.appendListeners(EventType.PRE_UPDATE, new PreUpdateEventListener() {
            @Override
            public boolean onPreUpdate(PreUpdateEvent event) {
                Object entity = event.getEntity();
                invoke(entity , PreUpdate.class);
                return false;
            }
        });
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

    }
}
