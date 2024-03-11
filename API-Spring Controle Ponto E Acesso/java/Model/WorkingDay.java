package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Entity

public class WorkingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    public WorkingDay(long id, String description){
        this.id = id;
        this.description = description;
    }

    public WorkingDay() {
        WorkingDay workingDay;
    }


    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingDay that = (WorkingDay) o;
        return id == that.id && Objects.equals(description, that.description);
    }
    @Override
    public int hashCode(){
        return Objects.hash(id, description);
    }


}
