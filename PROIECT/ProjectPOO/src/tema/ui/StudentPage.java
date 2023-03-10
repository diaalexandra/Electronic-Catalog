package tema.ui;

import tema.Catalog;
import tema.Course;
import tema.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class StudentPage extends JFrame {

    ArrayList<Course> courses;
    private ArrayList<Student> students;

    JList<String> courseList;
    DefaultListModel<String> courseListModel = new DefaultListModel<>();

    public StudentPage() throws HeadlessException {
        super("Student Page");

        this.setSize(250,250);
        setLayout(new BorderLayout());
        setVisible(true);


        JPanel userPanel = new JPanel();
        AtomicReference<JPanel> infoPanel = new AtomicReference<>(new JPanel());
        //JButton showInfo = new JButton(courses.get());
        JTextField firstName = new JTextField(20);
        JTextField lastName = new JTextField(20);




        //Refresh();

        //UI
        //JLabel courseLabel = new JLabel("Course: ");
        //courseList = new JList<String>();
        //courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //courseList.setModel(courseListModel);

        //courseList.setBackground(Color.BLUE);
        //courseList.setVisible(true);

        //JPanel coursePanel = new JPanel();
        //coursePanel.setLayout(new FlowLayout());
        //coursePanel.add(courseLabel);
        //coursePanel.add(courseList);
        //add(coursePanel);

        //add(courseList);

        //pack();
        //setSize(250,250);
        //setVisible(true);


    }

    //public void Refresh()
    //{
        //courses = Catalog.getInstance().GetCourses();
        //courseList.clearSelection();
        //courseListModel.clear();
        //for(Course c : courses)
        //{
        //    courseListModel.addElement(c.getName());
        //}

       // students = Course.
    //}

}
