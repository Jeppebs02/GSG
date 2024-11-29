package model;

import java.time.LocalDateTime;


public class Availability {
    private LocalDateTime date;
    private LocalDateTime time;
    private Unavailable status;

    // Constructor
    public Availability(LocalDateTime date, LocalDateTime time, Unavailable status) {
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Getters
    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Unavailable getStatus() {
        return status;
    }

    // Setters
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setStatus(Unavailable status) {
        this.status = status;
    }

    // Override toString() for better readability
    @Override
    public String toString() {
        return "Availability{" +
                "date=" + date +
                ", time=" + time +
                ", status=" + status +
                '}';
    }

  
}

