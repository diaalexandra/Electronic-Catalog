package tema;

import java.util.ArrayList;
import java.util.LinkedList;

public class Catalog implements Subject, Observer {
    public static ArrayList<Course> Courses= new ArrayList<>();

    private static Catalog obj = null;

    private Catalog() {
    }

    public static Catalog getInstance() {
        if (obj == null)
            obj = new Catalog();
        return obj;
    }

    public void addCourse(Course course) {
        Courses.add(course);
        course.addObserver(this);
    }

    public void removeCourse(Course course) {
        Courses.remove(course);
        course.removeObserver(this);
    }

    public Course getCourseByName(String name)
    {
        for (Course c : Courses) {
            if(c.getName().equals(name))
                return c;
        }
        return null;
    }

    public ArrayList<Course> getCourses()
    {
        return Courses;
    }

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
    }
    @Override
    public void update(Notification notification)
    {
        for (Observer o: observers)
        {
            o.update(notification);
        }
    }
    ArrayList<Observer> observers = new ArrayList<>();
}
