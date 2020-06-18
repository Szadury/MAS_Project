package com.pjwstk.MAS.BeerBar.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "PremiumUser_Id", nullable = false)
    private int userId;

    @Column(name = "Bar_Id", nullable = false)
    private int barId;

    @Column(name = "BarTable_Id", nullable = false)
    private String barTableId;

    @Column(name = "StartTime", nullable = false)
    private LocalDateTime startTime;

    @Column(name="EndTime", length = 30, nullable = false)
    private LocalDateTime endTime;

    @Column(name="Status", length = 20, nullable = false)
    private String Status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBarId() {
        return barId;
    }

    public void setBarId(int barId) {
        this.barId = barId;
    }

    public String getBarTableId() {
        return barTableId;
    }

    public void setBarTableId(String barTableId) {
        this.barTableId = barTableId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", barId=" + barId +
                ", barTableId='" + barTableId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", Status='" + Status + '\'' +
                '}';
    }
}
