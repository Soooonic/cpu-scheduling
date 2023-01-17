public class Process {
	private String name;
	private int arrival, burst, remainingBurst, quantum, timeElapsed, priority, waiting, turnaround;
	private Double percentile;
	Process(String name, int arrival, int brust, int priority, int quantum){
		this.name = name;
		this.arrival = arrival;
		this.burst = brust;
		this.remainingBurst = brust;
		this.priority = priority;
		this.quantum = quantum;
		this.timeElapsed = 0;
		this.percentile = 0d;
	}
	//to check if burst is 0
	int getRemainingBurst() {
		return remainingBurst;
	}
	int getPriority() {
		return priority;
	}
	int getTurnAroundTime() {
		return turnaround;
	}
	int getWaitingTime() {
		return waiting;
	}
	int getArrival() {
		return arrival;
	}
	int getRemainingQuantum() {
		return (quantum - timeElapsed);
	}
	//for output purpose
	String getName() {
		return name;
	}
	int getQuantum() {
		return quantum;
	}
	//run in the mode of FCFS and Priority
	int run() {
		percentile += 0.25;
		//calculate next percentile
		double runningTime = Math.ceil(percentile*quantum) - timeElapsed;
		//if it finishes before percentile
		runningTime = Math.min(runningTime, remainingBurst);
		//process takes the time and burst time decreases
		remainingBurst -= runningTime;
		timeElapsed += runningTime;
		return (int)runningTime;
	}
	//run in the mode of SJF
	int runSJF(int time) {
		int runningTime = Math.min(quantum - timeElapsed, time);
		runningTime = Math.min(remainingBurst, runningTime);
		remainingBurst -= runningTime;
		timeElapsed += runningTime;
		return runningTime;
	}
	void stop(SchedulingMode mode) {
		if(quantum - timeElapsed == 0) {
			quantum += 2;
		}
		else if(mode.equals(SchedulingMode.Priority)) {
			quantum += (int)Math.ceil((double)(quantum - timeElapsed)/2);
		}
		else if(mode.equals(SchedulingMode.SJF)) {
			quantum += (quantum - timeElapsed);
		}
		timeElapsed = 0;
		percentile = 0d;
	}
	void finish(int departureTime) {
		turnaround = (departureTime - arrival);
		waiting = turnaround - burst;
		quantum = 0;
		timeElapsed = 0;
	}
}