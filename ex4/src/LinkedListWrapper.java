import java.util.LinkedList;

public class LinkedListWrapper {
    private LinkedList<String> linkedList;
    private boolean isEmpty;

    LinkedListWrapper() {
        this.linkedList = new LinkedList<>();
        this.isEmpty = false;
    }

    public LinkedList<String> GetLinkedList(){
        return this.linkedList;
    }

    public boolean IsEmpty() {
        return this.isEmpty;
    }

    public void AddToLinkList(String val) {
        this.linkedList.add(val);
    }
}
