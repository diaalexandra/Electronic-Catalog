package tema;

import java.util.*;

public abstract class Course implements Subject {

    String name;
    Teacher teacher;
    Set<Assistant> assistants = new HashSet<>();
    ArrayList<Grade> grades = new ArrayList<>();
    Map<String , Group> groupMap = new HashMap<>();
    int creditPoints;
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Set<Assistant> getAssistants() {
        return assistants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }

    public void addAssistant(String ID, Assistant assistant) {
        if (!groupMap.containsKey(ID)) {
            groupMap.put(ID, new Group(ID, assistant));
        }

        if (!assistants.contains(assistant)) {
            assistants.add(assistant);
        }
    }

    public void addStudent(String ID, Student student) {
        if (groupMap.containsKey(ID)) {
            groupMap.get(ID).add(student);
        }
    }

    public Map<String, Group> getGroupMap() {
        return groupMap;
    }

    public void addGroup(Group group) {
        groupMap.put(group.getID(), group);
    }
    public void addGroup(String ID, Assistant assistant) {
        groupMap.put(ID, new Group(ID, assistant));
    }
    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp){
        groupMap.put(ID, new Group(ID, assistant, comp));
    }
    public Grade getGrade(Student student) {
        StudentComp c = new StudentComp();
        for (Grade g : grades)
        {
            if (c.compare(g.getStudent(), student) == 0)
            {
                return g;
            }
        }
        return null;
    }
    public void addGrade(Grade grade) {
        grades.add(grade);

        notifyObservers(grade);
    }
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> result = new ArrayList<>();
        for (Group g: groupMap.values())
        {
            for(int i = 0 ; i < g.size(); ++i)
            {
                Student student = g.get(i);
                if(!result.contains(student))
                    result.add(student);
            }
        }
        return  result;
    }
    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> result = new HashMap<>();
        for (Grade g: grades)
        {
            if (g.getStudent() != null) {
                result.put(g.getStudent(), g);
            }
        }
        return result;
    }

    public Student getStudentByName(String firstName, String lastName)
    {
        for (Group group: groupMap.values())
        {
            for(int i = 0 ; i < group.size(); ++i)
            {
                Student student = group.get(i);
                if (student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)) {
                    return student;
                }
            }
        }
        return null;
    }

    public User getTeacherByName(String firstName, String lastName)
    {
        if(teacher.getFirstName().equals(firstName) && teacher.getLastName().equals(lastName)) {
            return teacher;
        }

        for (Assistant assistant : assistants) {
            if(assistant.getFirstName().equals(firstName) && assistant.getLastName().equals(lastName)) {
                return assistant;
            }
        }

        return null;
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        Notification n = new Notification();
        n.setGrade(grade);
        n.setCourse(this);
        for(Observer o: observers)
        {
            o.update(n);
        }
    }

    ArrayList<Observer> observers = new ArrayList<>();

    public Student getBestStudent(){
        return gradesStrategy.getBestStudent(grades);
    }

    Strategy gradesStrategy = new BestTotalScore();
    public void setGradesStrategy(Strategy s){
        gradesStrategy = s;
    }

    private class Snapshot{
        ArrayList<Grade> grades = new ArrayList<>();
        public void saveGrades(ArrayList<Grade> gradeList) {
            grades.clear();
            for(Grade g : gradeList){
                grades.add((Grade) g.clone());
            }
        }
        public ArrayList<Grade> getGrades() {
            ArrayList<Grade> res = new ArrayList<>();
            for (Grade g : grades) {
                res.add((Grade) g.clone());
            }
            return res;
        }
    }
    Snapshot gradesSnapshot = new Snapshot();
    public void makeBackup() {
        gradesSnapshot.saveGrades(grades);
    }

    public void undo() {
        grades.clear();
        grades = gradesSnapshot.getGrades();
    }

    public abstract class CourseBuilder{
        protected Course obj;

        public void setTeacher(Teacher teacher) {
            obj.setTeacher(teacher);
        }
        public void setName(String name) {
            obj.setName(name);
        }
        public void setCreditPoints(int creditPoints) {
            obj.setCreditPoints(creditPoints);
        }
        public Course build(){
           return obj;
        }
    }
}
