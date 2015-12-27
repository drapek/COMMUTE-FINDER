package DrapekCollections;

import java.util.Arrays;

/**
 * Simple implemet of Array List made by Paweł Drapiewski as a part of COMMUTE-FINDER project
 * Created by drapek on 26.12.15.
 */
public class MyArrayList <E> {
    private int size = 0;
    private static final int START_CAPACITY = 10;
    private Object collection [];

    public MyArrayList() {
        collection = new Object[START_CAPACITY];
    }

    private void doubleCollectionSize(){
        int newCollectionSize = collection.length * 2;
        collection = Arrays.copyOf(collection, newCollectionSize);
    }

    public void add(E newElem) {
        if( collection.length == size)
            doubleCollectionSize();
        collection[size++] = newElem;
    }

    public E get(int index) {
        if( index >= size || index < 0)
            throw new IndexOutOfBoundsException("Given index: " + index + "is out of array range (0, " + this.size + ")" );

        return (E) collection[index];
    }

    public void delete(int index) {
        if( index >= size || index < 0)
            throw new IndexOutOfBoundsException("Given index: " + index + "is out of array range (0, " + this.size + ")" );


        collection[index] = null;

        for( int i = index; i < size - 1; i ++) {
            collection[i] = collection[i + 1];
        }

        this.size--;

    }

    /*
        @return negative value if can't find this object in collection
     */
    public int getIndex(E whichObject ) {
        for(int i = 0 ; i < size; i++) {
            if( collection[i].equals(whichObject) )
                return i;
        }
        return -1;
    }

    public int getSize() {
        return size;
    }

}
