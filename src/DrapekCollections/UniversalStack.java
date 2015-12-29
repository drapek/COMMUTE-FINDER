package DrapekCollections;

/**
 * Created by drapek on 26.12.15.
 */
public class UniversalStack <E> {
    private MyArrayList <E> stackDatabase = new MyArrayList<>();
    private int actualIndex = 0;

    public void push(E elem) {
        stackDatabase.add(elem);
        actualIndex++;
    }

    public E pop() {
        if( actualIndex == 0 )
            throw new IndexOutOfBoundsException("Stock is empty, but got query to give an element!");

        E retObject = stackDatabase.get(--actualIndex);
        stackDatabase.delete(actualIndex);

        return retObject;
    }

    /* probably unnecessary method */
    public boolean elementExist(E elem) {
        if (elem == null)
            return false;
        for(int i = 0; i < stackDatabase.getSize(); i++) {
            if( stackDatabase.get(i).equals(elem))
                return true;
        }

        return false;
    }

    public MyArrayList<E> copyActualStackAsArrayList() {
        MyArrayList <E> newTemp = new MyArrayList<>();

        for(int i = 0; i < stackDatabase.getSize(); i++) {
            newTemp.add(stackDatabase.get(i));
        }

        return newTemp;
    }

    public MyArrayList getStackDataBase() {
        return stackDatabase;
    }

}
