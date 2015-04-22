/*
 * Copyright 2015 Tagir Valeev
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.util.streamex;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.PrimitiveIterator.OfDouble;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

/**
 * A {@link DoubleStream} implementation with additional functionality
 * 
 * @author Tagir Valeev
 */
public class DoubleStreamEx implements DoubleStream {
    private final DoubleStream stream;

    DoubleStreamEx(DoubleStream stream) {
        this.stream = stream;
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public DoubleStreamEx unordered() {
        return new DoubleStreamEx(stream.unordered());
    }

    @Override
    public DoubleStreamEx onClose(Runnable closeHandler) {
        return new DoubleStreamEx(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }

    @Override
    public DoubleStreamEx filter(DoublePredicate predicate) {
        return new DoubleStreamEx(stream.filter(predicate));
    }

    @Override
    public DoubleStreamEx map(DoubleUnaryOperator mapper) {
        return new DoubleStreamEx(stream.map(mapper));
    }

    @Override
    public <U> StreamEx<U> mapToObj(DoubleFunction<? extends U> mapper) {
        return new StreamEx<>(stream.mapToObj(mapper));
    }

    @Override
    public IntStreamEx mapToInt(DoubleToIntFunction mapper) {
        return new IntStreamEx(stream.mapToInt(mapper));
    }

    @Override
    public LongStreamEx mapToLong(DoubleToLongFunction mapper) {
        return new LongStreamEx(stream.mapToLong(mapper));
    }

    @Override
    public DoubleStreamEx flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return new DoubleStreamEx(stream.flatMap(mapper));
    }

    @Override
    public DoubleStreamEx distinct() {
        return new DoubleStreamEx(stream.distinct());
    }

    /**
     * Returns a stream consisting of the elements of this stream in sorted
     * order. The elements are compared for equality according to
     * {@link java.lang.Double#compare(double, double)}.
     *
     * <p>This is a stateful intermediate operation.
     *
     * @return the new stream
     */
    @Override
    public DoubleStreamEx sorted() {
        return new DoubleStreamEx(stream.sorted());
    }

    @Override
    public DoubleStreamEx peek(DoubleConsumer action) {
        return new DoubleStreamEx(stream.peek(action));
    }

    @Override
    public DoubleStreamEx limit(long maxSize) {
        return new DoubleStreamEx(stream.limit(maxSize));
    }

    @Override
    public DoubleStreamEx skip(long n) {
        return new DoubleStreamEx(stream.skip(n));
    }

    @Override
    public void forEach(DoubleConsumer action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(DoubleConsumer action) {
        stream.forEachOrdered(action);
    }

    @Override
    public double[] toArray() {
        return stream.toArray();
    }

    @Override
    public double reduce(double identity, DoubleBinaryOperator op) {
        return stream.reduce(identity, op);
    }

    @Override
    public OptionalDouble reduce(DoubleBinaryOperator op) {
        return stream.reduce(op);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public double sum() {
        return stream.sum();
    }

    @Override
    public OptionalDouble min() {
        return stream.min();
    }

    @Override
    public OptionalDouble max() {
        return stream.max();
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public OptionalDouble average() {
        return stream.average();
    }

    @Override
    public DoubleSummaryStatistics summaryStatistics() {
        return stream.summaryStatistics();
    }

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(DoublePredicate predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public OptionalDouble findFirst() {
        return stream.findFirst();
    }

    @Override
    public OptionalDouble findAny() {
        return stream.findAny();
    }

    @Override
    public StreamEx<Double> boxed() {
        return new StreamEx<>(stream.boxed());
    }

    @Override
    public DoubleStreamEx sequential() {
        return new DoubleStreamEx(stream.sequential());
    }

    @Override
    public DoubleStreamEx parallel() {
        return new DoubleStreamEx(stream.parallel());
    }

    @Override
    public OfDouble iterator() {
        return stream.iterator();
    }

    @Override
    public java.util.Spliterator.OfDouble spliterator() {
        return stream.spliterator();
    }

    /**
     * Returns a new {@code DoubleStreamEx} which is a concatenation of this stream
     * and the stream containing supplied values
     * 
     * @param values the values to append to the stream
     * @return the new stream
     */
    public DoubleStreamEx append(double... values) {
        return new DoubleStreamEx(DoubleStream.concat(stream, DoubleStream.of(values)));
    }

    public DoubleStreamEx append(DoubleStream other) {
        return new DoubleStreamEx(DoubleStream.concat(stream, other));
    }

    /**
     * Returns a new {@code DoubleStreamEx} which is a concatenation of
     * the stream containing supplied values and this stream
     *  
     * @param values the values to prepend to the stream
     * @return the new stream
     */
    public DoubleStreamEx prepend(double... values) {
        return new DoubleStreamEx(DoubleStream.concat(DoubleStream.of(values), stream));
    }

    public DoubleStreamEx prepend(DoubleStream other) {
        return new DoubleStreamEx(DoubleStream.concat(other, stream));
    }

    public DoubleStreamEx remove(DoublePredicate predicate) {
        return new DoubleStreamEx(stream.filter(predicate.negate()));
    }

    public OptionalDouble findAny(DoublePredicate predicate) {
        return stream.filter(predicate).findAny();
    }

    public OptionalDouble findFirst(DoublePredicate predicate) {
        return stream.filter(predicate).findFirst();
    }

    public DoubleStreamEx sorted(Comparator<Double> comparator) {
        return new DoubleStreamEx(stream.boxed().sorted(comparator).mapToDouble(Double::doubleValue));
    }

    /**
     * Returns a stream consisting of the elements of this stream in reverse 
     * sorted order. The elements are compared for equality according to
     * {@link java.lang.Double#compare(double, double)}.
     *
     * <p>This is a stateful intermediate operation.
     *
     * @return the new stream
     * @since 0.0.8
     */
    public DoubleStreamEx reverseSorted() {
        return sorted((a, b) -> b.compareTo(a));
    }

    public <V extends Comparable<? super V>> DoubleStreamEx sortedBy(DoubleFunction<V> keyExtractor) {
        return new DoubleStreamEx(stream.boxed().sorted(Comparator.comparing(i -> keyExtractor.apply(i)))
                .mapToDouble(Double::doubleValue));
    }

    public DoubleStreamEx sortedByInt(DoubleToIntFunction keyExtractor) {
        return new DoubleStreamEx(stream.boxed().sorted(Comparator.comparingInt(i -> keyExtractor.applyAsInt(i)))
                .mapToDouble(Double::doubleValue));
    }

    public DoubleStreamEx sortedByLong(DoubleToLongFunction keyExtractor) {
        return new DoubleStreamEx(stream.boxed().sorted(Comparator.comparingLong(i -> keyExtractor.applyAsLong(i)))
                .mapToDouble(Double::doubleValue));
    }

    public DoubleStreamEx sortedByDouble(DoubleUnaryOperator keyExtractor) {
        return new DoubleStreamEx(stream.boxed().sorted(Comparator.comparingDouble(i -> keyExtractor.applyAsDouble(i)))
                .mapToDouble(Double::doubleValue));
    }

    public static DoubleStreamEx empty() {
        return new DoubleStreamEx(DoubleStream.empty());
    }

    /**
     * Returns a sequential {@code DoubleStreamEx} containing a single element.
     *
     * @param element the single element
     * @return a singleton sequential stream
     */
    public static DoubleStreamEx of(double element) {
        return new DoubleStreamEx(DoubleStream.of(element));
    }

    /**
     * Returns a sequential ordered {@code DoubleStreamEx} whose elements are the specified values.
     *
     * @param elements the elements of the new stream
     * @return the new stream
     */
    public static DoubleStreamEx of(double... elements) {
        return new DoubleStreamEx(DoubleStream.of(elements));
    }

    /**
     * Returns a sequential {@link DoubleStreamEx} with the specified range of the
     * specified array as its source.
     *
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return an {@code DoubleStreamEx} for the array range
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 0.1.1
     * @see Arrays#stream(double[], int, int)
     */
    public static DoubleStreamEx of(double[] array, int startInclusive, int endExclusive) {
        return new DoubleStreamEx(Arrays.stream(array, startInclusive, endExclusive));
    }
    
    /**
     * Returns a {@code DoubleStreamEx} object which wraps given {@link DoubleStream}
     * @param stream original stream
     * @return the wrapped stream
     * @since 0.0.8
     */
    public static DoubleStreamEx of(DoubleStream stream) {
        return stream instanceof DoubleStreamEx ? (DoubleStreamEx) stream : new DoubleStreamEx(stream);
    }

    /**
     * Returns a sequential {@code DoubleStreamEx} containing an {@link OptionalDouble} value, if
     * present, otherwise returns an empty {@code DoubleStreamEx}.
     *
     * @param optional the optional to create a stream of
     * @return a stream with an {@code OptionalDouble} value if present, otherwise an empty stream
     * @since 0.1.1
     */
    public static DoubleStreamEx of(OptionalDouble optional) {
        return optional.isPresent() ? of(optional.getAsDouble()) : empty();
    }
    
    public static DoubleStreamEx of(Collection<Double> c) {
        return new DoubleStreamEx(c.stream().mapToDouble(Double::doubleValue));
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * double} values, each between zero (inclusive) and one
     * (exclusive) produced by given {@link Random} object.
     *
     * <p>A pseudorandom {@code double} value is generated as if it's the result of
     * calling the method {@link Random#nextDouble()}.
     *
     * @param random a {@link Random} object to produce the stream from
     * @return a stream of pseudorandom {@code double} values
     * @see Random#doubles()
     */
    public static DoubleStreamEx of(Random random) {
        return new DoubleStreamEx(random.doubles());
    }

    public static DoubleStreamEx of(Random random, long streamSize) {
        return new DoubleStreamEx(random.doubles(streamSize));
    }

    public static DoubleStreamEx of(Random random, double randomNumberOrigin, double randomNumberBound) {
        return new DoubleStreamEx(random.doubles(randomNumberOrigin, randomNumberBound));
    }

    public static DoubleStreamEx of(Random random, long streamSize, double randomNumberOrigin, double randomNumberBound) {
        return new DoubleStreamEx(random.doubles(streamSize, randomNumberOrigin, randomNumberBound));
    }

    /**
     * Returns an infinite sequential ordered {@code DoubleStreamEx} produced by iterative
     * application of a function {@code f} to an initial element {@code seed},
     * producing a stream consisting of {@code seed}, {@code f(seed)},
     * {@code f(f(seed))}, etc.
     *
     * <p>The first element (position {@code 0}) in the {@code DoubleStreamEx} will be
     * the provided {@code seed}.  For {@code n > 0}, the element at position
     * {@code n}, will be the result of applying the function {@code f} to the
     * element at position {@code n - 1}.
     *
     * @param seed the initial element
     * @param f a function to be applied to to the previous element to produce
     *          a new element
     * @return A new sequential {@code DoubleStream}
     * @see DoubleStream#iterate(double, DoubleUnaryOperator)
     */
    public static DoubleStreamEx iterate(final double seed, final DoubleUnaryOperator f) {
        return new DoubleStreamEx(DoubleStream.iterate(seed, f));
    }

    /**
     * Returns an infinite sequential unordered stream where each element is
     * generated by the provided {@code DoubleSupplier}.  This is suitable for
     * generating constant streams, streams of random elements, etc.
     *
     * @param s the {@code DoubleSupplier} for generated elements
     * @return a new infinite sequential unordered {@code DoubleStreamEx}
     * @see DoubleStream#generate(DoubleSupplier)
     */
    public static DoubleStreamEx generate(DoubleSupplier s) {
        return new DoubleStreamEx(DoubleStream.generate(s));
    }
}
