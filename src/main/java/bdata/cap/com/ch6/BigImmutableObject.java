package bdata.cap.com.ch6;

import bdata.cap.com.util.ReadOnlyIterator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class BigImmutableObject implements
        Iterable<Entry<String, BigObject>> {
    private final HashMap<String, BigObject> registry;

    public BigImmutableObject(HashMap<String, BigObject> registry) {
        this.registry = registry;
    }

    public BigImmutableObject(BigImmutableObject prototype, String key,
                              BigObject newValue) {
        this(createRegistry(prototype, key, newValue));
    }

    @SuppressWarnings("unchecked")
    private static HashMap<String, BigObject> createRegistry(
            BigImmutableObject prototype, String key,
            BigObject newValue) {
        // 从现有对象中复制（浅复制）字段
        HashMap<String, BigObject> newRegistry =
                (HashMap<String, BigObject>) prototype.registry.clone();

        // 仅更新需要更新的部分
        newRegistry.put(key, newValue);
        return newRegistry;
    }

    @Override
    public Iterator<Entry<String, BigObject>> iterator() {
        // 对entrySet进行防御性复制
        final Set<Entry<String, BigObject>> readOnlyEntries = Collections
                .unmodifiableSet(registry.entrySet());

        // 返回一个只读的Iterator实例
        return ReadOnlyIterator.with(
                readOnlyEntries.iterator());
    }

    public BigObject getObject(String key) {
        return registry.get(key);
    }

    public BigImmutableObject update(String key,
                                     BigObject newValue) {
        return new BigImmutableObject(this, key, newValue);
    }
}

class BigObject {
    byte[] data = new byte[4 * 1024 * 1024];
    private int id;
    private final static AtomicInteger ID_Gen = new AtomicInteger(0);

    public BigObject() {
        id = ID_Gen.incrementAndGet();
    }

    @Override
    public String toString() {
        return "BigObject [id=" + id + "]";
    }
    // 省略其他代码
}