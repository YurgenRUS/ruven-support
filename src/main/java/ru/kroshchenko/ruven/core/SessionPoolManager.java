package ru.kroshchenko.ruven.core;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kroshchenko
 *         2015.12.24
 */
public class SessionPoolManager implements HttpSessionListener {

    private static final ConcurrentHashMap<String, WeakReference<HttpSession>> sessionPool = new ConcurrentHashMap<>();

    private static void addSession(HttpSession session) {
        if (sessionPool.get(session.getId()) == null) {
            sessionPool.putIfAbsent(session.getId(), new WeakReference<>(session));
        }
    }

    private static void removeSession(HttpSession session) {
        if (sessionPool.get(session.getId()) != null) {
            sessionPool.remove(session.getId());
        }
    }

    public static HttpSession findSession(String token) {
        HttpSession result = null;
        WeakReference<HttpSession> httpSessionWeakReference = sessionPool.get(token);
        if (httpSessionWeakReference != null) {
            result = httpSessionWeakReference.get();
        }
        return result;
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        addSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        removeSession(httpSessionEvent.getSession());
    }
}
