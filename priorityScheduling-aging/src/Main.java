import java.util.Scanner;

public class Main {
    public static void main(String []args){
        Scanner input=new Scanner(System.in);
        System.out.println("Enter the number of processes :");
        int size=input.nextInt();
        Process []p=new Process[size];
        for (int i = 0; i < size; i++) {
            System.out.println("Enter the name of process "+(i+1)+" :");
            String name=input.next();
            System.out.println("Enter the arrival time of process "+(i+1)+" :");
            int arrivalTime=input.nextInt();
            System.out.println("Enter the burst time of process "+(i+1)+" :");
            int burstTime=input.nextInt();
            System.out.println("Enter the priority of process "+(i+1)+" :");
            int priority=input.nextInt();
            p[i]=new Process(name,arrivalTime,burstTime,priority);
            System.out.println();
        }
        System.out.println("Enter the number of seconds after which the program will perform aging :");
        int agingNum=input.nextInt();
        PriorityScheduling ps=new PriorityScheduling(p,size,agingNum);
        ps.priorityAlgorithm();
    }
}
