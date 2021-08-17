package com.example.online_shopping.controller;

import com.example.online_shopping.erroe.ExceptionClass;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.request.ScheduleReq;
import com.example.online_shopping.services.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.online_shopping.util.Mappings.SCHEDULE;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(SCHEDULE)
public class ScheduleController {

    private final ScheduleService services;

    public ScheduleController(ScheduleService services) {
        this.services = services;
    }

    @GetMapping()
    public ResponseEntity<?> getSchedule() throws NotFound {
        return new ResponseEntity<>(services.getSchedule(), HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<?> addSchedule(@Valid @RequestBody ScheduleReq scheduleReq, BindingResult result) throws NotFound {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.addSchedule(scheduleReq), HttpStatus.CREATED);
    }

    @PutMapping("/{schedule_id}")
    public ResponseEntity<?> editSchedule(@PathVariable Long schedule_id, @Valid @RequestBody ScheduleReq req, BindingResult result) throws NotFound {
        ResponseEntity<?> errors = ExceptionClass.getErrors(result);
        if (errors != null) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services.editSchedule(schedule_id, req), HttpStatus.OK);
    }

    @DeleteMapping("/{schedule_id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long schedule_id) throws NotFound {
        return new ResponseEntity<>(services.deleteSchedule(schedule_id), HttpStatus.OK);
    }
}
