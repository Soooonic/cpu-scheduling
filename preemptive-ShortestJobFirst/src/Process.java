public class Process {
    private String name ;
    private int arrivalTime ;
    private int burstTime ;
    private int waitingTime ;
    private int turnAroundTime ;

    public Process(){
        this.name = "" ;
        this.burstTime = 0 ;
        this.arrivalTime = 0 ;
        this.waitingTime = 0 ;
        this.turnAroundTime = 0 ;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}
