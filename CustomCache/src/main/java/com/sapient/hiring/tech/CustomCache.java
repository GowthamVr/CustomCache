package com.sapient.hiring.tech;

import java.util.HashMap;
import java.util.Map;

public class CustomCache<K, V extends K> {

    private static final String ERROR_MESSAGE =
            "Object of class [%s] not allowable for this Key Type [%s]. "
                    + "Allowed types are [%s] or it sub and super types";

    private Map<K, V> map = new HashMap<>();

    public CustomCache(){
    }

    private Class getSuperClass(V value) {
        Class klass = value.getClass();
        while (klass.getSuperclass() != Object.class) {
            klass = klass.getSuperclass();
        }
        return klass;
    }

    public void put(K key, V value){
        if (!map.keySet().isEmpty()) {
            for (K keyInCache : map.keySet()) {
                if (key.getClass() == keyInCache.getClass()) {
                    Class klass = getSuperClass(map.get(keyInCache));
                    if (!klass.isAssignableFrom(value.getClass())) {
                        throw new IllegalArgumentException(
                                String.format(ERROR_MESSAGE, value.getClass(), key.getClass(), klass));
                    }
                }
            }
        }
        map.put(key, value);
    }

    public boolean remove(K key){
        V value = map.remove(key);
        return value != null;
    }


    public V get(K key){
        return map.get(key);
    }
}