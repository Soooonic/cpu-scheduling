import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.*;
public class PriorityScheduling {
    Process[] p;
    public int currentTime=0;
    int num=1;
    int agingNum;
    PriorityScheduling(Process[] p, int sz,int agingNum){
        this.p =p;
        this.num=sz;
        this.agingNum=agingNum;
    }
    LinkedList<Process>list=new LinkedList<>();
    public void priorityAlgorithm(){
        LinkedList<String>execution=new LinkedList<>();
        for (int i = 0; i < num; i++) {
            p[i].pid=i;
            list.addLast(p[i]);
        }
        sort();
        while (!list.isEmpty()){
            //in case no process has been arrived
            while (list.getFirst().arrivalTime>currentTime){
                currentTime++;
            }
            Process cur=list.pollFirst();
            execution.addLast(cur.name);
            cur.waitingTime+=currentTime-cur.lastProcessingTime;
            cur.remainingBurstTime--;
            currentTime++;
            cur.lastProcessingTime=currentTime;
            if(cur.remainingBurstTime>0){
                list.addFirst(cur);
            }
            else{
                cur.turnaroundTime=currentTime-cur.arrivalTime;
                p[cur.pid]=cur;
            }
            if(currentTime%agingNum==0) {
                aging();
            }
            sort();
        }
        //output
        double avgWaitingTime=0,avgTurnaroundTime=0;
        System.out.println("Processes execution order : ");
        for (int i = 0; i < execution.size(); i++) {
            System.out.print(execution.get(i)+" ");
        }
        System.out.println();
        for (int i = 0; i < num; i++) {
            avgWaitingTime+=p[i].waitingTime;
            avgTurnaroundTime+=p[i].turnaroundTime;
            System.out.println("Process: "+(p[i].pid+1)+" "+ "Name: "+p[i].name+" "+ "Waiting Time: "+p[i].waitingTime+ " "+"Turnaround Time: "+p[i].turnaroundTime);
        }
        avgWaitingTime=avgWaitingTime/num;
        avgTurnaroundTime=avgTurnaroundTime/num;
        System.out.println("Average Waiting Time: "+avgWaitingTime);
        System.out.println("Average Turnaround Time: "+avgTurnaroundTime);
    }
    public void aging(){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).arrivalTime<=currentTime) {
                list.get(i).priority = Math.max(0, list.get(i).priority - 1);
            }
        }
    }
    public void sort(){
        LinkedList<Process>l1=new LinkedList<>();
        LinkedList<Process>l2=new LinkedList<>();
        while (!list.isEmpty()){
            if(list.getFirst().arrivalTime==currentTime) {
                l1.addLast(list.pollFirst());
            }
            else{
                l2.addLast(list.pollFirst());
            }
        }
        l1.sort(new PriorityComparator());
        while (!l1.isEmpty()){
            list.addLast(l1.pollFirst());
        }
        if(!list.isEmpty()) {
            if(l2.getFirst().arrivalTime<list.getFirst().arrivalTime) {
                if (l2.getFirst().priority <= list.getFirst().priority) {
                    list.addFirst(l2.pollFirst());
                }
            }
        }
        l2.sort(new PriorityComparator());
        while (!l2.isEmpty()){
            list.addLast(l2.pollFirst());
        }
    }
}