package ru.seleznev.java_otus_2017_10.homework3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyArrayList<E> implements List<E> {
    public static final int DEFAULT_CAPACITY = 10;
    public int size = 0;
    private Object[] arr = initArr();

    private Object[] initArr() {
        return new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size();
    }

    @Override
    public boolean contains(Object o) {
        if (size() == 0 || o == null) {
            return false;
        }
        for (Object obj : arr) {
            if (o.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Integer currentIndex;

            @Override
            public boolean hasNext() {
                return nextIndex() < size();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException();
                }
                currentIndex = nextIndex();
                return (E)arr[currentIndex];
            }

            private int nextIndex() {
                return (null == currentIndex) ? 0 : currentIndex + 1;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(arr, arr.length);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return Arrays.copyOf(a, a.length);
    }

    @Override
    public boolean add(E e) {
        if (size()  == arr.length) {
            final int multiplier = arr.length > DEFAULT_CAPACITY ? arr.length : DEFAULT_CAPACITY;
            final Object[] newArr = new Object[2*multiplier];
            for (int i = 0; i < size(); i++) {
                newArr[i] = arr[i];
            }
            arr = newArr;
        }
        arr[size()] = e;
        sizeInc();
        return true;
    }

    private void sizeInc() {
        size++;
    }

    @Override
    public boolean remove(Object o) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("Input collection is null.");
        }
        if (c.isEmpty()) {
            return true;
        }
        for (final Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean res = false;
        for (E e : c) {
            res = add(e);
            if (!res) {
                return false;
            }
        }
        return res;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        size = 0;
        arr = initArr();
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.format("%i - input argument, %i - size", index, size()));
        }
        return (E)arr[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.format("%i - input argument, %i - size", index, size()));
        }
        final E res = (E) arr[index];
        arr[index] = element;
        return res;
    }

    @Override
    public void add(int index, E element) {
        // TODO:
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.format("%i - input argument, %i - size", index, size()));
        }
        final E res = (E) arr[index];
        final Object[] newArr = new Object[arr.length];
        for (int i = 0; i < size(); i++) {
            if (i == index) {
                continue;
            }
            newArr[i] = arr[i];
        }
        arr = newArr;
        return res;
    }

    @Override
    public int indexOf(Object o) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIterator<E>() {

            private Integer current;

            @Override
            public boolean hasNext() {
                if (size() == 0) {
                    return false;
                }
                if (current == null) {
                    return true;
                }
                return current < (size() - 1);
            }

            @Override
            public E next() {
                if (current == null) {
                    current = 0;
                    return (E) arr[0];
                }
                return (E) arr[current++];
            }

            @Override
            public boolean hasPrevious() {
                if (current == null) {
                    return false;
                }
                if (size() == 0) {
                    return false;
                }
                return true;
            }

            @Override
            public E previous() {
                if (current == null) {
                    throw new IllegalStateException();
                }
                if (current.equals(0)) {
                    current = null;
                    return (E) arr[0];
                }
                return (E) arr[current--];
            }

            @Override
            public int nextIndex() {
                if (current == null) {
                    return 0;
                }
                return current + 1;
            }

            @Override
            public int previousIndex() {
                if (current == null) {
                    return -1;
                }
                return current;
            }

            @Override
            public void remove() {
                if (current == null) {
                    throw new IllegalArgumentException();
                }
                if (size() == 0) {
                    throw new IllegalStateException();
                }
                System.arraycopy(arr, current + 1, arr, current, arr.length - current - 1);
                arr[arr.length - 1] = null;
                current = (current == 0) ? null : current - 1;
            }

            @Override
            public void set(E e) {
                if (current == null) {
                    throw new IllegalStateException();
                }
                arr[current] = e;
            }

            @Override
            public void add(E e) {
                if (current == null) {
                    throw new IllegalArgumentException();
                }
                if (size() == arr.length) {
                    // increase the size of the array
                    Object[] newArr = new Object[arr.length*2 + 1];
                    System.arraycopy(arr, 0, newArr, 0, arr.length);
                    arr = newArr;
                }
                System.arraycopy(arr, current, arr, current + 1, arr.length - current);
                set(e);
            }
        };
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "MyArrayList:" + Arrays.toString(Arrays.copyOfRange(arr, 0, size()));
    }
}
