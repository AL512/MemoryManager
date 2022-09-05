public class MemorySegment {
    public int length; // длина отрезка
    public Memory.SegmentStatus status;
    public MemorySegment()
    {
        length = 0;
        status = Memory.SegmentStatus.IS_FREE;
    }
}
