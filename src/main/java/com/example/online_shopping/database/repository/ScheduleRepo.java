package com.example.online_shopping.database.repository;

import com.example.online_shopping.database.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
}
