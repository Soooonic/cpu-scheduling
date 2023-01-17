
public enum SchedulingMode {
	FCFS, Priority, SJF;
	private static final SchedulingMode[] vals = values();
    public SchedulingMode next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}