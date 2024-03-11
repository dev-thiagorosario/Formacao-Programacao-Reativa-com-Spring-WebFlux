package Service;

import Model.WorkingDay;
import Repository.WorkingDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkingDayService {

    @Autowired
    private WorkingDayRepository workingDayRepository;

    public WorkingDay saveWorkingDay(WorkingDay workingDay){
        return workingDayRepository.save(workingDay);
    }

    public List<WorkingDay> findALL(){
        return workingDayRepository.findAll();
    }

    public Optional<WorkingDay> getByID(Long idWorking){
        return workingDayRepository.findById(idWorking);
    }

    public WorkingDay updateWorking(WorkingDay workingDay){
        return workingDayRepository.save(workingDay);
    }

    public void deleteWorking(Long idWorking){
        workingDayRepository.deleteById(idWorking);
    }
}
