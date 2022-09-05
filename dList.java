public class dList {

    Node head = null;
    Node tail = null;

    public void pushBack(MemorySegment segment)
    {
        Node node = new Node();
        node.data = segment;

        if (head == null)
        {
            head = tail = node;
            return;
        }

        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    public void pushFront(MemorySegment segment)
    {
        Node node = new Node();
        node.data = segment;

        if (head == null)
        {
            head = tail = node;
            return;
        }

        node.next = head;
        head.prev = node;
        head = node;
    }

    void popBack()
    {
        if (head == null)
            return;

        Node temp = tail.prev;
        tail = temp;
        tail.next = null;
    }

    void popFront()
    {
        if (head == null)
            return;

        Node temp = head.next;
        head = temp;
        head.prev = null;
    }

    public void remove(Node node)
    {
        if (node.prev != null)
            node.prev.next = node.next;

        if (node.next != null)
            node.next.prev = node.prev;
    }

    Node get(int idx)
    {
        if (head == null)
            return null;

        Node curNode = head;

        for (int i = 0; curNode.next != null && i < idx; i++) {
            curNode = curNode.next;
        }

        return curNode;
    }

    void set(int idx, MemorySegment val)
    {
        if (head == null)
            return;

        Node curNode = head;

        for (int i = 0; curNode.next != null && i < idx; i++)
        {
            curNode = curNode.next;
        }
        curNode.data = val;
    }

    MemorySegment back()
    {
        if (isEmpty())
            return null;
        return tail.data;
    }

    MemorySegment front()
    {
        if (isEmpty())
            return null;

        return head.data;
    }

    boolean isEmpty()
    {
        return head == null;
    }
}

