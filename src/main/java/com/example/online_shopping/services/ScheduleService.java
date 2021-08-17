package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.Schedule;
import com.example.online_shopping.database.repository.ScheduleRepo;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.ScheduleReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ScheduleService {

    private ScheduleRepo scheduleRepo;

    @Autowired
    public ScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }


    public List<Schedule> getSchedule() throws NotFound {
        try {
            return scheduleRepo.findAll();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "Not fount");
            throw new NotFound("Get Schedule", -2, error);
        }
    }


    public List<Schedule> addSchedule(ScheduleReq req) throws NotFound {
        try {
            Schedule schedule = new Schedule(req.getType(), req.getSchedule_time());
            scheduleRepo.save(schedule);
            log.info("Create Schedule: {}", schedule.toString());
            return scheduleRepo.findAll();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The schedule has not been saved!");
            throw new NotFound("Save Schedule", -2, error);
        }
    }


    public List<Schedule> editSchedule(Long schedule_id, ScheduleReq req) throws NotFound {
        try {
            Schedule schedule = scheduleRepo.findById(schedule_id).get();
            schedule.setType(req.getType());
            schedule.setSchedule_time(req.getSchedule_time());
            scheduleRepo.save(schedule);
            return scheduleRepo.findAll();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The schedule has not been edited!");
            throw new NotFound("Edit Schedule", -2, error);
        }
    }


    public List<Schedule> deleteSchedule(Long schedule_id) throws NotFound {
        try {
            scheduleRepo.deleteById(schedule_id);
            return scheduleRepo.findAll();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The schedule has not been deleted!");
            throw new NotFound("Delete Schedule", -2, error);
        }
    }
}
