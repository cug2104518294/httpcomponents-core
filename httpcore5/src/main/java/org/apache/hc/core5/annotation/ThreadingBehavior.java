package org.apache.hc.core5.annotation;

/**
 Defines types of threading behavior enforced at runtime.
 */
public enum ThreadingBehavior {

    /**
     * Instances of classes with the given contract are expected to be fully immutable
     * and thread-safe.
     *
     * 具有给定合同的类的实例应该是完全不可变的和线程安全的。
     */
    IMMUTABLE,

    /**
     * Instances of classes with the given contract are expected to be immutable if their
     * dependencies injected at construction time are immutable and are expected to be thread-safe
     * if their dependencies are thread-safe.
     */
    IMMUTABLE_CONDITIONAL,

    /**
     * Instances of classes with the given contract are expected to maintain no state
     * and to be thread-safe.
     */
    STATELESS,

    /**
     * Instances of classes with the given contract are expected to be fully thread-safe.
     */
    SAFE,

    /**
     * Instances of classes with the given contract are expected to be thread-safe if their
     * dependencies injected at construction time are thread-safe.
     */
    SAFE_CONDITIONAL,

    /**
     * Instances of classes with the given contract are expected to be non thread-safe.
     */
    UNSAFE

}
