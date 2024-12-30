public class Notes {
    private int Note;
    private int Count;
    public Notes(int Note, int Count) {
        this.setNote(Note);
        this.setCount(Count);
    }
    public int getNote() {
        return Note;
    }

    public int setNote(int note) {
        Note = note;
        return Note;
    }
    public int getCount() {
        return Count;
    }

    public int setCount(int count) {
        Count = count;
        return Count;
    }
}
