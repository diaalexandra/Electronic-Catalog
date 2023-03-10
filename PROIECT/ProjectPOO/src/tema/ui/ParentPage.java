package tema.ui;

import tema.Catalog;
import tema.Notification;
import tema.Observer;
import tema.Subject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class ParentPage extends JFrame implements Observer {
    private JPanel windowContents;
    public ParentPage() throws HeadlessException {
        super("Parent Page");

        setSize(1000,500);
        setVisible(true);
        setLayout(new BorderLayout());

        windowContents = new JPanel();
        windowContents.setLayout(new BoxLayout(windowContents, BoxLayout.PAGE_AXIS));
        windowContents.add(Box.createRigidArea(new Dimension(0, 5)));

        JScrollPane sp = new JScrollPane(windowContents);
        add(sp, BorderLayout.CENTER);
        revalidate();

        Catalog.getInstance().addObserver(this);
    }

    @Override
    public void update(Notification notification) {
        JPanel nPanel = new JPanel();
        nPanel.setBackground(Color.LIGHT_GRAY);
        nPanel.setLayout(new FlowLayout());
        nPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        String name = String.format("Student: %s %s", notification.getGrade().getStudent().getFirstName(),
                notification.getGrade().getStudent().getLastName());
        JLabel studentName = new JLabel(name);
        nPanel.add(studentName);
        nPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        String course = String.format("Course: %s",notification.getCourse().getName());
        JLabel courseName = new JLabel(course);
        nPanel.add(courseName);
        nPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        String grade = String.format("Partial: %s ; Exam: %s ; Total: %s",
                notification.getGrade().getPartialScore(),
                notification.getGrade().getExamScore(),
                notification.getGrade().getTotal());
        JLabel gradeValues = new JLabel(grade);
        nPanel.add(gradeValues);

        windowContents.add(nPanel);
        windowContents.add(Box.createRigidArea(new Dimension(0, 5)));
        revalidate();
    }
}
