/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.inject.beans;

import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.annotation.UsedByGeneratedCode;
import io.micronaut.core.beans.BeanConstructor;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanMethod;
import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.beans.UnsafeBeanProperty;
import io.micronaut.core.reflect.ClassUtils;
import io.micronaut.core.reflect.ReflectionUtils;
import io.micronaut.core.reflect.exception.InstantiationException;
import io.micronaut.core.type.Argument;
import io.micronaut.core.type.ReturnType;
import io.micronaut.core.util.ArgumentUtils;
import io.micronaut.inject.ExecutableMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Abstract implementation of the {@link BeanIntrospection} interface. This class is subclasses at compilation time by generated byte code and should not be used directly.
 * <p>
 * Implementation is using method dispatch to access the bean instance.
 *
 * @param <B> The bean type
 * @author Denis Stepanov
 * @since 3.1
 */
public abstract class AbstractInitializableBeanIntrospection<B> implements BeanIntrospection<B> {

    private final Class<B> beanType;
    private final AnnotationMetadata annotationMetadata;
    private final AnnotationMetadata constructorAnnotationMetadata;
    private final Argument<?>[] constructorArguments;
    private final List<BeanProperty<B, Object>> beanProperties;
    private final List<BeanMethod<B, Object>> beanMethods;

    private BeanConstructor<B> beanConstructor;

    public AbstractInitializableBeanIntrospection(Class<B> beanType,
                                                  AnnotationMetadata annotationMetadata,
                                                  AnnotationMetadata constructorAnnotationMetadata,
                                                  Argument<?>[] constructorArguments,
                                                  BeanPropertyRef<Object>[] propertiesRefs,
                                                  BeanMethodRef<Object>[] methodsRefs) {
        this.beanType = beanType;
        this.annotationMetadata = annotationMetadata == null ? AnnotationMetadata.EMPTY_METADATA : annotationMetadata;
        this.constructorAnnotationMetadata = constructorAnnotationMetadata == null ? AnnotationMetadata.EMPTY_METADATA : constructorAnnotationMetadata;
        this.constructorArguments = constructorArguments == null ? Argument.ZERO_ARGUMENTS : constructorArguments;
        if (propertiesRefs != null) {
            List<BeanProperty<B, Object>> beanProperties = new ArrayList<>(propertiesRefs.length);
            for (BeanPropertyRef beanPropertyRef : propertiesRefs) {
                beanProperties.add(new BeanPropertyImpl<>(beanPropertyRef));
            }
            this.beanProperties = Collections.unmodifiableList(beanProperties);
        } else {
            this.beanProperties = Collections.emptyList();
        }
        if (methodsRefs != null) {
            List<BeanMethod<B, Object>> beanMethods = new ArrayList<>(methodsRefs.length);
            for (BeanMethodRef beanMethodRef : methodsRefs) {
                beanMethods.add(new BeanMethodImpl<>(beanMethodRef));
            }
            this.beanMethods = Collections.unmodifiableList(beanMethods);
        } else {
            this.beanMethods = Collections.emptyList();
        }
    }

    /**
     * Reflection free bean instantiation implementation for the given arguments.
     *
     * @param arguments The arguments
     * @return The bean
     */
    @NonNull
    @Internal
    @UsedByGeneratedCode
    protected abstract B instantiateInternal(@Nullable Object[] arguments);

    /**
     * Obtain a property by its index.
     *
     * @param index The index of the property
     * @return A bean property
     */
    @Internal
    @UsedByGeneratedCode
    protected BeanProperty<B, Object> getPropertyByIndex(int index) {
        return beanProperties.get(index);
    }

    /**
     * Triggers the invocation of the method at index.
     *
     * @param index  The method index
     * @param target The target
     * @param args   The arguments
     * @param <V>    The result type
     * @return The result
     */
    @Nullable
    @UsedByGeneratedCode
    protected <V> V dispatch(int index, @NonNull B target, @Nullable Object[] args) {
        throw unknownDispatchAtIndexException(index);
    }

    /**
     * Triggers the invocation of the method at index for a single argument call.
     * Allowing to not wrap a single argument in an object array.
     *
     * @param index  The method index
     * @param target The target
     * @param arg    The argument
     * @param <V> The result type
     * @return The result
     */
    @Nullable
    @UsedByGeneratedCode
    protected <V> V dispatchOne(int index, @NonNull Object target, @Nullable Object arg) {
        throw unknownDispatchAtIndexException(index);
    }

    /**
     * Creates a new exception when the dispatch at index is not found.
     *
     * @param index The method index
     * @return The exception
     */
    @UsedByGeneratedCode
    protected final RuntimeException unknownDispatchAtIndexException(int index) {
        return new IllegalStateException("Unknown dispatch at index: " + index);
    }

    /**
     * Get all the bean properties annotated for the given type.
     * Nullable result method version of {@link #getIndexedProperty(Class, String)}.
     *
     * @param annotationType  The annotation type
     * @param annotationValue The annotation value
     * @return A immutable collection of properties.
     * @see io.micronaut.core.annotation.Introspected#indexed()
     */
    @Nullable
    @UsedByGeneratedCode
    public BeanProperty<B, Object> findIndexedProperty(@NonNull Class<? extends Annotation> annotationType, @NonNull String annotationValue) {
        return null;
    }

    @NonNull
    @Override
    @UsedByGeneratedCode
    public Collection<BeanProperty<B, Object>> getIndexedProperties(@NonNull Class<? extends Annotation> annotationType) {
        return Collections.emptyList();
    }

    /**
     * Returns subset of bean properties defined by an array of indexes.
     *
     * @param indexes The indexes
     * @return a collection of bean properties
     */
    @UsedByGeneratedCode
    protected Collection<BeanProperty<B, Object>> getBeanPropertiesIndexedSubset(int[] indexes) {
        if (indexes.length == 0) {
            return Collections.emptyList();
        }
        return new IndexedCollections<>(indexes, beanProperties);
    }

    @Override
    public B instantiate() throws InstantiationException {
        throw new InstantiationException("No default constructor exists");
    }

    @NonNull
    @Override
    public B instantiate(boolean strictNullable, Object... arguments) throws InstantiationException {
        ArgumentUtils.requireNonNull("arguments", arguments);

        if (arguments.length == 0) {
            return instantiate();
        }

        final Argument<?>[] constructorArguments = getConstructorArguments();
        if (constructorArguments.length != arguments.length) {
            throw new InstantiationException("Argument count [" + arguments.length + "] doesn't match required argument count: " + constructorArguments.length);
        }

        for (int i = 0; i < constructorArguments.length; i++) {
            Argument<?> constructorArgument = constructorArguments[i];
            final Object specified = arguments[i];
            if (specified == null) {
                if (constructorArgument.isDeclaredNullable() || !strictNullable) {
                    continue;
                } else {
                    throw new InstantiationException("Null argument specified for [" + constructorArgument.getName() + "]. If this argument is allowed to be null annotate it with @Nullable");
                }
            }
            if (!ReflectionUtils.getWrapperType(constructorArgument.getType()).isInstance(specified)) {
                throw new InstantiationException("Invalid argument [" + specified + "] specified for argument: " + constructorArgument);
            }
        }

        return instantiateInternal(arguments);
    }

    @Override
    public BeanConstructor<B> getConstructor() {
        if (beanConstructor == null) {
            beanConstructor = new BeanConstructor<B>() {
                @Override
                public Class<B> getDeclaringBeanType() {
                    return beanType;
                }

                @Override
                public Argument<?>[] getArguments() {
                    return constructorArguments;
                }

                @Override
                public B instantiate(Object... parameterValues) {
                    return AbstractInitializableBeanIntrospection.this.instantiate(parameterValues);
                }

                @Override
                public AnnotationMetadata getAnnotationMetadata() {
                    return constructorAnnotationMetadata;
                }
            };
        }
        return beanConstructor;
    }

    @Override
    public Argument<?>[] getConstructorArguments() {
        return constructorArguments;
    }

    @NonNull
    @Override
    public Optional<BeanProperty<B, Object>> getIndexedProperty(@NonNull Class<? extends Annotation> annotationType, @NonNull String annotationValue) {
        return Optional.ofNullable(findIndexedProperty(annotationType, annotationValue));
    }

    @NonNull
    @Override
    public Optional<BeanProperty<B, Object>> getProperty(@NonNull String name) {
        ArgumentUtils.requireNonNull("name", name);
        int index = propertyIndexOf(name);
        return index == -1 ? Optional.empty() : Optional.of(beanProperties.get(index));
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return annotationMetadata;
    }

    @NonNull
    @Override
    public Collection<BeanProperty<B, Object>> getBeanProperties() {
        return beanProperties;
    }

    @NonNull
    @Override
    public Class<B> getBeanType() {
        return beanType;
    }

    @NonNull
    @Override
    public Collection<BeanMethod<B, Object>> getBeanMethods() {
        return beanMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractInitializableBeanIntrospection<?> that = (AbstractInitializableBeanIntrospection<?>) o;
        return Objects.equals(beanType, that.beanType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanType);
    }

    @Override
    public String toString() {
        return "BeanIntrospection{type=" + beanType + '}';
    }

    /**
     * A subset collection that is defined by an array of indexes into other collection.
     *
     * @param <T> The item type
     */
    private static final class IndexedCollections<T> extends AbstractCollection<T> {

        private final int[] indexed;
        private final List<T> list;

        private IndexedCollections(int[] indexed, List<T> list) {
            this.indexed = indexed;
            this.list = list;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {

                int i = -1;

                @Override
                public boolean hasNext() {
                    return i + 1 < indexed.length;
                }

                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    int index = indexed[++i];
                    return list.get(index);
                }
            };
        }

        @Override
        public int size() {
            return indexed.length;
        }

    }

    /**
     * Implementation of {@link BeanProperty} that is using {@link BeanPropertyRef} and method dispatch.
     *
     * @param <P> The property type
     */
    private final class BeanPropertyImpl<P> implements UnsafeBeanProperty<B, P> {

        private final BeanPropertyRef<P> ref;
        private final Class<?> typeOrWrapperType;

        private BeanPropertyImpl(BeanPropertyRef<P> ref) {
            this.ref = ref;
            this.typeOrWrapperType = ReflectionUtils.getWrapperType(getType());
        }

        @NonNull
        @Override
        public String getName() {
            return ref.argument.getName();
        }

        @NonNull
        @Override
        public Class<P> getType() {
            return ref.argument.getType();
        }

        @Override
        @NonNull
        public Argument<P> asArgument() {
            return ref.argument;
        }

        @NonNull
        @Override
        public BeanIntrospection<B> getDeclaringBean() {
            return AbstractInitializableBeanIntrospection.this;
        }

        @Override
        public AnnotationMetadata getAnnotationMetadata() {
            return ref.argument.getAnnotationMetadata();
        }

        @Nullable
        @Override
        public P get(@NonNull B bean) {
            ArgumentUtils.requireNonNull("bean", bean);
            if (!beanType.isInstance(bean)) {
                throw new IllegalArgumentException("Invalid bean [" + bean + "] for type: " + beanType);
            }
            if (isWriteOnly()) {
                throw new UnsupportedOperationException("Cannot read from a write-only property");
            }
            return dispatchOne(ref.getMethodIndex, bean, null);
        }

        @Override
        public P getUnsafe(B bean) {
            return dispatchOne(ref.getMethodIndex, bean, null);
        }

        @Override
        public void set(@NonNull B bean, @Nullable P value) {
            ArgumentUtils.requireNonNull("bean", bean);

            if (!beanType.isInstance(bean)) {
                throw new IllegalArgumentException("Invalid bean [" + bean + "] for type: " + bean);
            }
            if (isReadOnly()) {
                throw new UnsupportedOperationException("Cannot write a read-only property: " + getName());
            }
            if (value != null && !typeOrWrapperType.isInstance(value)) {
                throw new IllegalArgumentException("Specified value [" + value + "] is not of the correct type: " + getType());
            }
            dispatchOne(ref.setMethodIndex, bean, value);
        }

        @Override
        public void setUnsafe(B bean, P value) {
            dispatchOne(ref.setMethodIndex, bean, value);
        }

        @Override
        public B withValue(@NonNull B bean, @Nullable P value) {
            ArgumentUtils.requireNonNull("bean", bean);
            if (!beanType.isInstance(bean)) {
                throw new IllegalArgumentException("Invalid bean [" + bean + "] for type: " + beanType);
            }
            return withValueUnsafe(bean, value);
        }

        @Override
        public B withValueUnsafe(B bean, P value) {
            if (value == getUnsafe(bean)) {
                return bean;
            } else if (ref.withMethodIndex == -1) {
                if (!ref.readyOnly && ref.setMethodIndex != -1) {
                    dispatchOne(ref.setMethodIndex, bean, value);
                    return bean;
                }
                return UnsafeBeanProperty.super.withValue(bean, value);
            } else {
                return dispatchOne(ref.withMethodIndex, bean, value);
            }
        }

        @Override
        public boolean isReadOnly() {
            return ref.readyOnly;
        }

        @Override
        public boolean isWriteOnly() {
            return ref.getMethodIndex == -1 && (ref.setMethodIndex != -1 || ref.withMethodIndex != -1);
        }

        @Override
        public boolean hasSetterOrConstructorArgument() {
            return ref.mutable;
        }

        @Override
        public String toString() {
            return "BeanProperty{" +
                    "beanType=" + beanType +
                    ", type=" + ref.argument.getType() +
                    ", name='" + ref.argument.getName() + '\'' +
                    '}';
        }
    }

    /**
     * Implementation of {@link BeanMethod} that is using {@link BeanMethodRef} and method dispatch.
     *
     * @param <P> The property type
     */
    private final class BeanMethodImpl<P> implements BeanMethod<B, P>, ExecutableMethod<B, P> {

        private final BeanMethodRef<P> ref;

        private BeanMethodImpl(BeanMethodRef<P> ref) {
            this.ref = ref;
        }

        @NonNull
        @Override
        public BeanIntrospection<B> getDeclaringBean() {
            return AbstractInitializableBeanIntrospection.this;
        }

        @Override
        public @NonNull
        ReturnType<P> getReturnType() {
            //noinspection unchecked
            return new ReturnType() {
                @Override
                public Class<P> getType() {
                    return ref.returnType.getType();
                }

                @Override
                @NonNull
                public Argument<P> asArgument() {
                    return ref.returnType;
                }

                @Override
                public Map<String, Argument<?>> getTypeVariables() {
                    return ref.returnType.getTypeVariables();
                }

                @NonNull
                @Override
                public AnnotationMetadata getAnnotationMetadata() {
                    return ref.returnType.getAnnotationMetadata();
                }
            };
        }

        @NonNull
        @Override
        public AnnotationMetadata getAnnotationMetadata() {
            return ref.annotationMetadata == null ? AnnotationMetadata.EMPTY_METADATA : ref.annotationMetadata;
        }

        @NonNull
        @Override
        public String getName() {
            return ref.name;
        }

        @Override
        public Argument<?>[] getArguments() {
            return ref.arguments == null ? Argument.ZERO_ARGUMENTS : ref.arguments;
        }

        @Override
        public P invoke(@NonNull B instance, Object... arguments) {
            return dispatch(ref.methodIndex, instance, arguments);
        }

        @Override
        public Method getTargetMethod() {
            if (ClassUtils.REFLECTION_LOGGER.isWarnEnabled()) {
                ClassUtils.REFLECTION_LOGGER.warn("Using getTargetMethod for method {} on type {} requires the use of reflection. GraalVM configuration necessary", getName(), getDeclaringType());
            }
            return ReflectionUtils.getRequiredMethod(getDeclaringType(), getMethodName(), getArgumentTypes());
        }

        @Override
        public Class<B> getDeclaringType() {
            return getDeclaringBean().getBeanType();
        }

        @Override
        public String getMethodName() {
            return getName();
        }

    }

    /**
     * Bean property compile-time data container.
     *
     * @param <P> The property type.
     */
    @Internal
    @UsedByGeneratedCode
    public static final class BeanPropertyRef<P> {
        @NonNull
        final Argument<P> argument;

        final int getMethodIndex;
        final int setMethodIndex;
        final int withMethodIndex;
        final boolean readyOnly;
        final boolean mutable;

        public BeanPropertyRef(@NonNull Argument<P> argument,
                               int getMethodIndex,
                               int setMethodIndex,
                               int valueMethodIndex,
                               boolean readyOnly, boolean mutable) {
            this.argument = argument;
            this.getMethodIndex = getMethodIndex;
            this.setMethodIndex = setMethodIndex;
            this.withMethodIndex = valueMethodIndex;
            this.readyOnly = readyOnly;
            this.mutable = mutable;
        }
    }

    /**
     * Bean method compile-time data container.
     *
     * @param <P> The property type.
     */
    @Internal
    @UsedByGeneratedCode
    public static final class BeanMethodRef<P> {
        @NonNull
        final Argument<P> returnType;
        @NonNull
        final String name;
        @Nullable
        final AnnotationMetadata annotationMetadata;
        @Nullable
        final Argument<?>[] arguments;

        final int methodIndex;

        public BeanMethodRef(@NonNull Argument<P> returnType,
                             @NonNull String name,
                             @Nullable AnnotationMetadata annotationMetadata,
                             @Nullable Argument<?>[] arguments,
                             int methodIndex) {
            this.returnType = returnType;
            this.name = name;
            this.annotationMetadata = annotationMetadata;
            this.arguments = arguments;
            this.methodIndex = methodIndex;
        }
    }

}
