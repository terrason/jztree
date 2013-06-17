/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.servlet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author terrason
 */
public class Context<V> implements Map<String, V> {

    private static final ThreadLocal<Context> threadLocalContext = new ThreadLocal<Context>();
    private static final String USER_PRINCIPAL_KEY = "current_user_principal";

    public static Context currentContext() {
        Context c = threadLocalContext.get();
        if (c == null) {
            c = new Context();
            threadLocalContext.set(c);
        }
        return c;
    }
    private Map<String, V> context = new HashMap<String, V>();

    @Override
    public int size() {
        return context.size();
    }

    @Override
    public boolean isEmpty() {
        return context.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return context.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return context.containsValue(o);
    }

    @Override
    public V get(Object o) {
        return context.get(o);
    }

    @Override
    public V put(String k, V v) {
        return context.put(k, v);
    }

    @Override
    public V remove(Object o) {
        return context.remove(o);
    }

    @Override
    public void putAll(Map map) {
        context.putAll(map);
    }

    @Override
    public void clear() {
        context.clear();
    }

    @Override
    public Set<String> keySet() {
        return context.keySet();
    }

    @Override
    public Collection<V> values() {
        return context.values();
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return context.entrySet();
    }

    public Object getPrincipal() {
        return context.get(USER_PRINCIPAL_KEY);
    }

    public void setPrincipal(Object principal) {
        context.put(USER_PRINCIPAL_KEY, (V) principal);
    }
}
