package DrapekCollections;

/**
 * Created by drapek on 26.12.15.
 */
public class UniversalStack <E> {
    private MyArrayList <E> stackDatabase = new MyArrayList<>();
    private int acutalIndex = 0;

    public void push(E elem) {
        stackDatabase.add(elem);
        acutalIndex++;
    }

    public E pop() {
        if( acutalIndex == 0 )
            throw new IndexOutOfBoundsException("Stock is empty, but got query to give an element!");

        E retObject = stackDatabase.get(--acutalIndex);
        stackDatabase.delete(acutalIndex);

        return retObject;
    }
}
