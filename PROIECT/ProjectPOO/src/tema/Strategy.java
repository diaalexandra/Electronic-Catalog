package tema;

import java.util.ArrayList;

public interface Strategy {
    Student getBestStudent(ArrayList<Grade> grades);
}
