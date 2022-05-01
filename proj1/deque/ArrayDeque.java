package deque;


public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    private int jia(int index, int num){
        return (index+num)%items.length;
    }

    private int jian(int index,int num){
        return (index+(num/items.length+1)*items.length- num)%items.length;
    }


    /** Creates an empty list. */
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = items.length-1;
        nextLast=0;
    }

    public void printDeque(){
        int current_index = jia(nextFirst,1);
        for (int i=0;i<size;i++){
            System.out.print(items[current_index]+" ");
            current_index = jia(current_index,1);
        }
        System.out.println();
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];

        if (nextFirst>=nextLast){
            int simple_length = nextLast;
            int strange_length = items.length-nextFirst-1;

            System.arraycopy(items, 0, a, 0, simple_length);

            System.arraycopy(items, jia(nextFirst,1), a, (a.length -strange_length)%a.length, strange_length);

            nextFirst = a.length-strange_length-1;

        }else {
            System.arraycopy(items, jia(nextFirst,1), a, 0, size);
            nextFirst = a.length-1;
            nextLast=size;
        }

        items = a;
    }

    /** Inserts X into the back of the list. */
    public void addLast(Item x) {
        if (nextFirst == nextLast) {
            resize((int) (size * 2));
        }

        items[nextLast] = x;
        size = size + 1;
        nextLast = jia(nextLast,1);
    }



    public Item removeLast() {

        if (size==0){
            return null;
        }
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        int last_index = jian(nextLast,1);
        Item x = items[last_index];
        items[last_index] = null;
        nextLast=last_index;
        size = size - 1;
        return x;
    }


    public void addFirst(Item x) {
        if (nextFirst == nextLast) {
            resize((int) (size * 2));
        }

        items[nextFirst] = x;
        size = size + 1;
        nextFirst = jian(nextFirst,1);
    }


    public Item removeFirst(){

        if (size==0){
            return null;
        }
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }

        int first_index = jia(nextFirst,1);

        Item x = items[first_index];
        items[first_index] = null;
        nextFirst=first_index;
        size = size - 1;
        return x;
    }

    /** Returns the item from the back of the list. */

    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {

        return items[jia(nextFirst+1,i)];


    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */

}