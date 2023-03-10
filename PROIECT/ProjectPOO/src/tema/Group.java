package tema;

import java.util.ArrayList;
import java.util.Comparator;

public class Group extends ArrayList<Student> {
    private String ID;
    private Assistant assistant;
    private Comparator<Student> comp;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = comp;
    }
    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
        comp = new StudentComp();
    }

    public String getID() {
        return ID;
    }

    public Assistant getAssistant() {
        return assistant;
    }
}
