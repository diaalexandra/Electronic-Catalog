package tema;

import java.util.ArrayList;

public class BestTotalScore implements Strategy{
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        Grade bestGrade = grades.get(0);
        for (int i = 2; i < grades.size(); ++i){
            Grade g = grades.get(i);
            if(g.getTotal() > bestGrade.getTotal()){
                bestGrade = g;
            }
        }
        return bestGrade.getStudent();
    }
}
