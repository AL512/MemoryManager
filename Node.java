public class Node
{
    public MemorySegment data;
    public Node next;
    public Node prev;
    public Node()
    {
        data = new MemorySegment();
        next = null;
        prev = null;
    }
};