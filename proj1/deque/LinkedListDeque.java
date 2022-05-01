package deque;

public class LinkedListDeque<shit_things> {

    private IntNode sentinel;

    private int size;

    public class IntNode {
        public IntNode prev;
        public shit_things item;
        public IntNode next;
        public IntNode(IntNode p,shit_things i, IntNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel= new IntNode(null,null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public int size() {
        return size;
    }

    public void addLast(shit_things x) {
        IntNode p = sentinel;

        IntNode new_node = new IntNode(p.prev, x, p);
        p.prev.next = new_node;
        p.prev=new_node;
        size += 1;
    }

    public void addFirst(shit_things x) {
        IntNode p = sentinel;

        IntNode new_node = new IntNode(p, x, p.next);
        p.next.prev = new_node;
        p.next=new_node;
        size += 1;
    }

    public shit_things removeFirst() {
        IntNode p = sentinel;
        IntNode old_first_node = p.next;
        IntNode new_first_node = old_first_node.next;
        p.next = new_first_node;
        new_first_node.prev=p;
        if (size>0){
            size -= 1;
        }
        return old_first_node.item;

    }

    public shit_things removeLast() {
        IntNode p = sentinel;
        IntNode old_last_node = p.prev;
        IntNode new_last_node = old_last_node.prev;
        p.prev = new_last_node;
        new_last_node.next=p;
        if (size>0){
            size -= 1;
        }

        return old_last_node.item;
    }

    public shit_things get(int i) {

        IntNode p = sentinel;

        for (int j=0;j<=i;j++){
            p = p.next;
        }
        return p.item;
    }

    public shit_things getRecursive(int index){
        IntNode p = sentinel;
        return getRecursiveHelper(p.next,index);

    }

    private shit_things getRecursiveHelper(IntNode p, int index) {
        if (p == sentinel) {
            return null;
        }
        if (index == 0) {
            return  p.item;
        }
        return getRecursiveHelper(p.next, index - 1);
    }

    public boolean equals(Object o){

        if (!(o instanceof LinkedListDeque)){
            return false;
        }else {

            return true;
        }

    }

    public boolean isEmpty() {
        return size == 0;

    }

    public void printDeque() {
        IntNode p = sentinel;
        while (p.next.item!=null){
            System.out.print(p.next.item+" ");
            p = p.next;
        }
    }

}
