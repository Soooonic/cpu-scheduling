import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String []args) {
		int processNum, quantum, contextSwitching, arrival, burst, priority;
		String name;
		LinkedList<Process> processes = new LinkedList<Process>();
		Queue<String> processesHistory = new LinkedList<String>();
		Queue<Integer> timeStamps = new LinkedList<Integer>();
		Scheduler scheduler;
		
		System.out.print("please enter nubmer of processes: ");
		processNum = scanner.nextInt();
		scanner.nextLine();
		
		System.out.print("\nplease enter contextSwitching time: ");
		contextSwitching = scanner.nextInt();
		scanner.nextLine();
		
		for(int i = 0; i < processNum; ++i) {
			System.out.println("process " + (i + 1));
			System.out.println("please enter process name: ");
			name = scanner.nextLine();
			
			System.out.print("please enter process burst time: ");
			burst = scanner.nextInt();
			scanner.nextLine();
			
			System.out.print("please enter process arrival time: ");
			arrival = scanner.nextInt();
			scanner.nextLine();
			
			System.out.print("please enter process priority: ");
			priority = scanner.nextInt();
			scanner.nextLine();
			
			System.out.print("please enter quantum time: ");
			quantum = scanner.nextInt();
			scanner.nextLine();
			
			processes.add(new Process(name, arrival, burst, priority, quantum));
		}
		
		scheduler = new Scheduler(processes, contextSwitching, processesHistory, timeStamps);
		scheduler.run();
		printHistory(processesHistory, timeStamps);
		printTimes(processes);
	}
	
	static void printHistory(Queue<String> processesHistory, Queue<Integer> timeStamps) {
		System.out.println("---------------------------------------------------------------------------------------------");
		while(!processesHistory.isEmpty()) {
			System.out.println(processesHistory.poll() + "\t" + timeStamps.poll() + " : " + timeStamps.poll());
		}
	}
	
	static void printTimes(LinkedList<Process> processes) {
		System.out.println("---------------------------------------------------------------------------------------------");
		Double avgWaiting = 0d, avgTurnAround = 0d;
		int n = processes.size();
		for(Process process: processes) {
			System.out.println(process.getName());
			System.out.println("Waiting time is " + process.getWaitingTime());
			System.out.println("Turn Around time is " + process.getTurnAroundTime() + "\n");
			avgWaiting += (double)process.getWaitingTime()/n;
			avgTurnAround += (double)process.getTurnAroundTime()/n;
		}
		System.out.println("Average waiting time is " + avgWaiting);
		System.out.println("Average Turn around time is " + avgTurnAround);
	}
}