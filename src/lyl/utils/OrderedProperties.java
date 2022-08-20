package lyl.utils;


import java.util.*;

// 有读写顺序的Properties类
public class OrderedProperties extends Properties {

    private static final long serialVersionUID = -4627607243846121965L;

    private final LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<>();

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(linkedHashSet);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        linkedHashSet.add(key);
        return super.put(key, value);
    }

    @Override
    public synchronized Object remove(Object key) {
        linkedHashSet.remove(key);
        return super.remove(key);
    }

    @Override
    public Set<Object> keySet() {
        return linkedHashSet;
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<>();

        for (Object key : this.linkedHashSet) {
            set.add((String) key);
        }
        return set;
    }
}