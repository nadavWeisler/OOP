import java.util.LinkedList;

public class LinkedListWrapper {
    private LinkedList<String> linkedList;

    public LinkedListWrapper() {
        this.linkedList = new LinkedList<>();
    }

    public LinkedList<String> GetLinkedList(){
        return this.linkedList;
    }

    public boolean IsEmpty() {
        return !this.GetLinkedList().isEmpty();
    }

    public void AddToLinkList(String val) {
        this.linkedList.add(val);
    }

    public boolean RemoveFromLinkList(String val) {
        if(this.linkedList.contains(val)) {
            this.linkedList.remove(val);
            return true;
        } else {
            return false;
        }
    }
}
