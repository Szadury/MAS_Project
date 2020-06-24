package com.pjwstk.MAS.BeerBar.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "PremiumUser_Id", nullable = false)
    private int userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Bar_Id", nullable = false)
    private Bar bar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BarTable_Id", nullable = false)
    private BarTable barTable;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @Column(name = "StartTime", nullable = false)
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @Column(name="EndTime", length = 30, nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="Status", length = 20, nullable = false)
    private StatusType Status;

    public static int reservationTime = 2;

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

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public BarTable getBarTable() {
        return barTable;
    }

    public void setBarTable(BarTable barTable) {
        this.barTable = barTable;
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

    public StatusType getStatus() {
        return Status;
    }

    public void setStatus(StatusType status) {
        Status = status;
    }

    public static int getReservationTime() {
        return reservationTime;
    }

    public static void setReservationTime(int reservationTime) {
        Reservation.reservationTime = reservationTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", barTable=" + barTable +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", Status=" + Status +
                '}';
    }

    public enum StatusType{
        Pending,
        Accepted,
        Rejected,
        Canceled,
        Finished
    }
}
