import java.util.*;

public class CpuScheduling {
    public void SRTF(){ // preemptive shortest job first
        Vector<Process> processes = new Vector<>(0) ;
        Map<String,Integer>iniTialBurstTime = new HashMap<>();
        // input
        System.out.println("Enter The Number Of Processes :");
        Scanner intValue = new Scanner(System.in) ;
        Scanner strValue = new Scanner(System.in) ;
        int n = intValue.nextInt() ;
        System.out.println("Enter the name , arrival time , and burst time of each process");
        for (int i=0 ; i<n ; ++i){
            Process process = new Process() ;
            System.out.println("name : ");
            process.setName(strValue.next());
            System.out.println("arrival time : ");
            process.setArrivalTime(intValue.nextInt());
            System.out.println("burst time : ");
            process.setBurstTime(intValue.nextInt());
            iniTialBurstTime.put(process.getName(),process.getBurstTime()) ;
            processes.add(process) ;
        }
        // processing
        String currentProcessName = "" ;
        String lastCurrent = currentProcessName ;
        int currentTime = 0 ;
        while (true){
            int cnt = 0 ;
            for(int i=0 ; i<processes.size() ; ++i){
                //when cnt = size : all processes has finished it's job
                if(processes.get(i).getBurstTime()==0){
                    cnt++ ;
                    continue;
                }
                if(processes.get(i).getArrivalTime()<=currentTime){ // existing process
                    int currentProcessInd = -1 ;
                    //  get index of the current process
                    for(int j=0 ; j<processes.size() ; j++){
                        if(processes.get(j).getName()==currentProcessName)
                            currentProcessInd = j ;
                    }
                   if(currentProcessName.equals(""))   // this is the first process
                       currentProcessName = processes.get(i).getName() ;
                   // else : compare the remaining time of this process and the current process
                   else if(processes.get(i).getBurstTime()<processes.get(currentProcessInd).getBurstTime()){
                       currentProcessName = processes.get(i).getName() ;
                   }
                }
            }
            if(cnt==processes.size())   // all has finished
                break;
            if(lastCurrent!=currentProcessName){
                if(currentTime==0){ // no context switch before , because this the first process
                    System.out.println(currentProcessName + " got in at " + currentTime);
                }
                else{   // there is context switch , add the context switch time .
                    currentTime+=1 ;    // contextSwitch of the last process
                    System.out.println(currentProcessName + " got in at " + currentTime);
                }
            }
            // update current process burst time
            for(int i=0 ; i<processes.size() ; i++){
                if(processes.get(i).getName()==currentProcessName){
                    // set the burst time with the new time
                    int remBurstTime = processes.get(i).getBurstTime() ;
                    remBurstTime-- ;
                    processes.get(i).setBurstTime(remBurstTime);    // remaining burst time in the next iteration
                    if(processes.get(i).getBurstTime()==0){
                        currentProcessName = "" ;
                        int arrival = processes.get(i).getArrivalTime() ;
                        int turnAround = ((currentTime+1)-arrival) ;
                        turnAround+=1 ; // contextSwitch after the next iteration ( note that the current time has not changed )
                        if(processes.get(i).getTurnAroundTime()==0){ // if the time not set before
                            // set the turn around time ( ending time - arrival time )
                            processes.get(i).setTurnAroundTime(turnAround);
                            // set the waiting time ( turnAroundTime - burst time )
                            processes.get(i).setWaitingTime(turnAround- iniTialBurstTime.get(processes.get(i).getName()));
                        }
                    }
                    break;
                }
            }
            lastCurrent = currentProcessName ;
            currentTime++ ;
        }
        // waiting time and avg waiting time
        double avgWaitingTime = 0 ;
        double avgTurnAroundTime = 0 ;
        System.out.println("the waiting and turn around time of the processes");
        for(int i=0 ; i<processes.size() ; i++){
            System.out.println(processes.get(i).getName()+" , waiting time : "+processes.get(i).getWaitingTime()+" , turn around time : "+processes.get(i).getTurnAroundTime());
            avgWaitingTime+=processes.get(i).getWaitingTime();
            avgTurnAroundTime+=processes.get(i).getTurnAroundTime();
        }
        avgWaitingTime/=(1.0*processes.size()) ;
        avgTurnAroundTime/=(1.0*processes.size()) ;
        System.out.println("the average waiting time : "+avgWaitingTime) ;
        System.out.println("the average turn around time : "+avgTurnAroundTime) ;
    }
}
