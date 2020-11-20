import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Array;
import java.util.*;

public class CriticalPath {
    public static int maxCost;
    public static String format = "%1$-10s %2$-5s %3$-5s %4$-5s %5$-5s %6$-5s %7$-10s\n";
    public static Calculate calculate = new Calculate();

    static JTable jt;

    public static void main(String[] args) {
        // The example dependency graph
        HashSet<Task> allTasks = new HashSet<Task>();
        Task end = new Task("End", 0);
        Task F = new Task("F", 2, end);
        Task A = new Task("A", 3, end);
        Task X = new Task("X", 4, F, A);
        Task Q = new Task("Q", 2, A, X);
        Task start = new Task("Start", 0, Q);
        allTasks.add(end);
        allTasks.add(F);
        allTasks.add(A);
        allTasks.add(X);
        allTasks.add(Q);
        allTasks.add(start);
        Task[] result = calculate.criticalPath(allTasks);
        print(result);
        // System.out.println("Critical Path: " + Arrays.toString(result));

        JFrame f;
        f = new JFrame();
        jt.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(jt);
        f.add(sp);
        f.setSize(700, 400);
        f.setVisible(true);
    }

    // A wrapper class to hold the tasks during the calculation
    public static class Task {
        // the actual cost of the task
        public int cost;
        // the cost of the task along the critical path
        public int criticalCost;
        // a name for the task for printing
        public String name;
        // the earliest start
        public int earlyStart;
        // the earliest finish
        public int earlyFinish;
        // the latest start
        public int latestStart;
        // the latest finish
        public int latestFinish;
        // the tasks on which this task is dependant
        public HashSet<Task> dependencies = new HashSet<Task>();

        public Task(String name, int cost, Task... dependencies) {
            this.name = name;
            this.cost = cost;
            for (Task t : dependencies) {
                this.dependencies.add(t);
            }
            this.earlyFinish = -1;
        }

        public void setLatest() {
            latestStart = maxCost - criticalCost;
            latestFinish = latestStart + cost;
        }

        public String[] toStringArray() {
            //Whether the task is critical or not
            String criticalCond = earlyStart == latestStart ? "Yes" : "No";
            String[] toString = {name, earlyStart + "", earlyFinish + "", latestStart + "", latestFinish + "",
                    latestStart - earlyStart + "", criticalCond};


            //TODO: Use table model from JVMCW1

            return toString;
        }
    }

    public static void print(Task[] tasks) {
        System.out.format(format, "Task", "ES", "EF", "LS", "LF", "Slack", "Critical?");
        String[][] data = {};
        String[] column = {"TASK", "Earliest Start Date", "Earliest Finish Date", "Late Start Date", "Late Finish Date", "Slack", "Critical"};
        DefaultTableModel tableModel = new DefaultTableModel(data, column);
        jt = new JTable(tableModel);
        for (Task t : tasks) {
            System.out.format(format, (Object[]) t.toStringArray());
            tableModel.addRow(t.toStringArray());
        }

    }
}
