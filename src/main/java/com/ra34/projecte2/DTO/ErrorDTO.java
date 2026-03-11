package com.ra34.projecte2.DTO;

public class ErrorDTO {
    private int status;
    private String message;

    public ErrorDTO() {
    }

    public ErrorDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    
}
