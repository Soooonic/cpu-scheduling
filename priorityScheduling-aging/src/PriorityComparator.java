import java.util.Comparator;

public class PriorityComparator implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        if(o1.priority>o2.priority){
            return 1;
        }
        else if(o1.priority<o2.priority){
            return -1;
        }
        else{
            return 0;
        }
    }
}