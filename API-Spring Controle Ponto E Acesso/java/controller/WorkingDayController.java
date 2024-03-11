package controller;

import Model.WorkingDay;
import Service.WorkingDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/working")
public class WorkingDayController {

    @Autowired
    private WorkingDayService workingDayService;

    @PostMapping("/create")
    public WorkingDay createWorkingDay(@RequestBody WorkingDay workingDay) {
        return workingDayService.saveWorkingDay(workingDay);
    }

    @GetMapping
    public List<WorkingDay> getWorkingList() {
        return workingDayService.findALL();
    }

    @GetMapping("/{id}")
    public WorkingDay getWorkingByID(@PathVariable Long id) {
        WorkingDay workingDay = workingDayService.getByID(id);
        if (workingDay == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkingDay not found with ID: " + id);
        }
        return workingDay;
    }

}
