import java.util.Stack;

public class Memory {

    final int MEMORY_CAPACITY = 1000;

    enum SegmentStatus
    {
        IS_FREE, // отрезок свободен, в нём можно выделять память
        IS_ALLOCATED, // отрезок занят
        IS_DELETED, // отрезок невалиден, его нужно удалить вместе со всеми указателями на него (деталь реализации)
    };

    dList memorySegments;
    Stack<Node> freeSegments;
    Node[] arr = new Node[MEMORY_CAPACITY];
    int idx = 0;

    public Memory() {
        MemorySegment segment = new MemorySegment();
        segment.length = MEMORY_CAPACITY - 1;
        segment.status = SegmentStatus.IS_FREE;

        Node node = new Node();
        node.data = segment;

        memorySegments.pushBack(segment);
        freeSegments.push(node);
    }

    int malloc(int size)
    {
        Node freeNode = new Node();
        while (true)
        {
            if (freeSegments.empty())
                return -1;

            Node node = freeSegments.pop();

            if (node.data.status == SegmentStatus.IS_FREE)
            {
                freeNode = node;
                break;
            }
        }

        if (freeNode.data.length < size)
            return -1;

        int diff = freeNode.data.length - size;

        if (diff > 0)
        {
            Node newFreeSegment = new Node();
            newFreeSegment.prev = freeNode;
            newFreeSegment.next = freeNode.next;
            newFreeSegment.data.length = diff;
            newFreeSegment.data.status = SegmentStatus.IS_FREE;

            freeNode.next = newFreeSegment;
            freeSegments.push(newFreeSegment);
        }

        freeNode.data.length = size;
        freeNode.data.status = SegmentStatus.IS_ALLOCATED;

        arr[idx] = freeNode;

        int ret = idx;
        idx++;

        return ret;
    }

    int free(int index)
    {
        if (index >= arr.length)
            return -1;

        if (arr[index] == null)
            return -1;

        if (arr[index].data.status != SegmentStatus.IS_ALLOCATED)
        return -1;

        Node node = arr[index];
        arr[index] = null;
        int size = 0;
        size += node.data.length;

        if (node.prev != null)
        {
            if (node.prev.data.status == SegmentStatus.IS_FREE)
            {
                size += node.prev.data.length;
                node.prev.data.status = SegmentStatus.IS_DELETED;
                memorySegments.remove(node.prev);
            }
        }

        if (node.next != null)
        {
            if (node.next.data.status == SegmentStatus.IS_FREE)
            {
                size += node.next.data.length;
                node.next.data.status = SegmentStatus.IS_DELETED;
                memorySegments.remove(node.next);
            }
        }

        memorySegments.remove(node);

        MemorySegment newSegment = new MemorySegment();
        newSegment.length = size;
        newSegment.status = SegmentStatus.IS_FREE;

        memorySegments.set(index, newSegment);
        Node newNode = memorySegments.get(index);
        freeSegments.push(newNode);

        return 0;
    }
}
