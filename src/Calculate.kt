
import CriticalPath.maxCost
import java.util.*


class Calculate {

    fun criticalPath(tasks: Set<CriticalPath.Task>?): Array<CriticalPath.Task?>? {
        // tasks whose critical cost has been calculated
        val completed = HashSet<CriticalPath.Task>()
        // tasks whose critical cost needs to be calculated
        val remaining = HashSet(tasks)

        // Backflow algorithm
        // while there are tasks whose critical cost isn't calculated.
        while (!remaining.isEmpty()) {
            var progress = false

            // find a new task to calculate
            val it = remaining.iterator()
            while (it.hasNext()) {
                val task = it.next()
                if (completed.containsAll(task.dependencies)) {
                    // all dependencies calculated, critical cost is max
                    // dependency
                    // critical cost, plus our cost
                    var critical = 0
                    for (t in task.dependencies) {
                        if (t.criticalCost > critical) {
                            critical = t.criticalCost
                        }
                    }
                    task.criticalCost = critical + task.cost
                    // set task as calculated an remove
                    completed.add(task)
                    it.remove()
                    // note we are making progress
                    progress = true
                }
            }
            // If we haven't made any progress then a cycle must exist in
            // the graph and we wont be able to calculate the critical path
            if (!progress) throw RuntimeException("Cyclic dependency, algorithm stopped!")
        }

        // get the cost
        maxCost(tasks!!)
        val initialNodes = initials(tasks)
        calculateEarly(initialNodes!!)

        // get the tasks
        val ret: Array<CriticalPath.Task?> = completed.toTypedArray()
        // create a priority list
        Arrays.sort(ret) { o1, o2 -> o1!!.name.compareTo(o2!!.name) }


        return ret
    }

    fun calculateEarly(initials: HashSet<CriticalPath.Task>) {
        for (initial in initials) {
            initial.earlyStart = 0
            initial.earlyFinish = initial.cost
            setEarly(initial)
        }
    }

    fun setEarly(initial: CriticalPath.Task) {
        val completionTime = initial.earlyFinish
        for (t in initial.dependencies) {
            if (completionTime >= t.earlyStart) {
                t.earlyStart = completionTime
                t.earlyFinish = completionTime + t.cost
            }
            setEarly(t)
        }
    }

    fun initials(tasks: Set<CriticalPath.Task>): HashSet<CriticalPath.Task>? {
        val remaining = HashSet(tasks)
        for (t in tasks) {
            for (td in t.dependencies) {
                remaining.remove(td)
            }
        }
        print("Initial nodes: ")
        for (t in remaining) print(t.name + " ")
        print("\n\n")
        return remaining
    }

    fun maxCost(tasks: Set<CriticalPath.Task>) {
        var max = -1
        for (t in tasks) {
            if (t.criticalCost > max) max = t.criticalCost
        }
        maxCost = max
        println("Critical path length (cost): " + maxCost)
        for (t in tasks) {
            t.setLatest()
        }
    }
}