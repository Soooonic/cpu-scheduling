import java.util.Comparator;
public class Process {
    public int remainingBurstTime;
    public String name;
    public int waitingTime;
    public int lastProcessingTime;
    public int burstTime;
    public int priority;
    public int arrivalTime;
    public int turnaroundTime;
    public int pid;

    public Process(String name,int arrivalTime, int burstTime , int priority) {
        this.burstTime = burstTime;
        this.priority = priority;
        this.name = name;
        this.arrivalTime=arrivalTime;
        this.remainingBurstTime=burstTime;
        this.lastProcessingTime=arrivalTime;
    }

}
