import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	private int contextSwitchTime, currentTime;
	private SchedulingMode currentMode;
	private Process currentProcess;
	private LinkedList<Process> readyQ, processQ;
	private Queue<String> processes;
	private Queue<Integer> timeStamps;
	Scheduler(LinkedList<Process> queue, int contextSwitchTime, Queue<String> processesHistory, Queue<Integer> timeStamps){
		this.processQ = queue;
		this.contextSwitchTime = contextSwitchTime;
		this.processes = processesHistory;
		this.timeStamps = timeStamps;
		readyQ = new LinkedList<Process>();
		currentProcess = null;
		currentTime = 0;
	}
	void getNewProcesses() {
		int nearest = -1;
		for(Process process: processQ) {
			if(process.getRemainingBurst() == 0) continue;
			if(process == currentProcess) continue;
			if(readyQ.contains(process)) continue;
			
			if(nearest == -1) nearest = process.getArrival();
			else nearest = Math.min(process.getArrival(), nearest);
		}
		
		if(nearest == -1) return;
		if(nearest > currentTime && !readyQ.isEmpty()) return;
		
		nearest = Math.max(currentTime, nearest);
		
		for(Process process: processQ) {
			if(process.getRemainingBurst() == 0) continue;
			if(process == currentProcess) continue;
			if(readyQ.contains(process)) continue;
			
			if(process.getArrival() > nearest) continue;
			readyQ.addLast(process);
		}
		currentTime = nearest;
	}
	void run() {
		currentMode = SchedulingMode.FCFS;
		
		//load new processes
		getNewProcesses();
		
		while((!readyQ.isEmpty()) || (currentProcess != null)) {
			
			Process nextProcess;
			//get candidate next process
			if(currentMode.equals(SchedulingMode.FCFS)) nextProcess = getFirst();
			else if(currentMode.equals(SchedulingMode.Priority))nextProcess = getHighestPriority();
			else nextProcess = getShortestTime();
				
			//switches current with candidate if eligible
			Switch(currentProcess, nextProcess);
			if(currentMode.equals(SchedulingMode.FCFS))printQuantumUpdate();
			
			//run process. Two different algorithms because SJF algorithm is different than FCFS and Priority
			if(currentMode.equals(SchedulingMode.SJF)) {
				currentTime += currentProcess.runSJF(getStopingTime(currentProcess.getRemainingBurst()));
			}
			else {
				currentTime += currentProcess.run();
				currentMode = currentMode.next();
			}
			
			//after process finished running it might be done
			if(currentProcess.getRemainingBurst() == 0) {
				currentProcess.finish(currentTime);
				currentMode = SchedulingMode.FCFS;
				//record time when process finished
				timeStamps.add(currentTime);
				currentProcess = null;
			}
			//after process finished running it might be out of quantum time
			else if(currentProcess.getRemainingQuantum() == 0) {
				currentProcess.stop(currentMode);
				currentMode = SchedulingMode.FCFS;
				readyQ.addLast(currentProcess);
				//record time when process stopped
				timeStamps.add(currentTime);
				currentTime += contextSwitchTime;
				currentProcess = null;
			}
			//add new processes that came at current time to ready queue
			getNewProcesses();
		}
	}
	//gets process with first in ready queue
	//get first process in ready queue
	Process getFirst() {
		return readyQ.getFirst();
	}
	//gets process with highest priority in ready queue
	//get process with highest priority in ready queue
	Process getHighestPriority() {
		Process best = null;
		for(Process process: readyQ) {
			if(best == null) best = process;
			else if(process.getPriority() < best.getPriority()) best = process;
		}
		return best;
	}
	//gets process with shortest job time in ready queue
	//get process with shortest job time in ready queue
	Process getShortestTime() {
		Process best = null;
		for(Process process: readyQ) {
			if(best == null) best = process;
			else if(process.getRemainingBurst() < best.getRemainingBurst()) best = process;
		}
		return best;
	}
	//finds out when to interrupt in SJF
	int getStopingTime(int burst) {
		int stoppingTime = currentTime+burst+1;
		for(Process process: processQ) {
			if(process.getArrival() < currentTime) continue;
			if(process.getRemainingBurst() < burst - (process.getArrival() - currentTime)) {
				stoppingTime = Math.min(stoppingTime, process.getArrival());
			}
		}
		return stoppingTime;
	}
	//switches two processes
	void Switch(Process a, Process b) {
		//ensure that candidate process is eligible
		if(a != null && b != null) {
			if(currentMode.equals(SchedulingMode.Priority)) {
				if(a.getPriority() <= b.getPriority()) b = null;
			}
			else if(currentMode.equals(SchedulingMode.SJF)) {
				if(a.getRemainingBurst() <= b.getRemainingBurst()) b = null;
			}
		}
		//if a new process will enter with no previous process
		if(a == null && b != null) {
			currentMode = SchedulingMode.FCFS;
			currentProcess = b;
			readyQ.remove(b);
			//add the time when the process got in
			timeStamps.add(currentTime);
			
			processes.add(b.getName());
		}
		//if a new process will enter with a previous process was running
		else if(a != null && b != null){
			a.stop(currentMode);
			currentMode = SchedulingMode.FCFS;
			readyQ.addLast(a);
			readyQ.remove(b);
			currentProcess = b;
			
			//add the time when the second process got out
			timeStamps.add(currentTime);
			
			currentTime += contextSwitchTime;
			
			//add the time when the second process got in
			timeStamps.add(currentTime);
			
			processes.add(currentProcess.getName());
		}
	}
	
	void printQuantumUpdate() {
		System.out.println("*******************************************Quantum Update************************************");
		System.out.println("current time " + currentTime);
		for(Process process: processQ) {
			System.out.print(process.getName() + "\t");
		}
		System.out.println();
		for(Process process: processQ) {
			System.out.print(process.getQuantum() + "\t");
		}
		System.out.println();
	}
}