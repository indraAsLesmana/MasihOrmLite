package com.tutor93.tugasfrensky.latihanapi;

import java.io.Serializable;
import java.util.List;

/**
 * Created by frensky on 7/31/15.
 */
public class LatihanAPIResponse implements Serializable{
    boolean status;
    String message;
    List<DealingAPIResponse> dealing_data;
    List<PictureAPIResponse> employee_picture;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DealingAPIResponse> getDealing_data() {
        return dealing_data;
    }

    public void setDealing_data(List<DealingAPIResponse> dealing_data) {
        this.dealing_data = dealing_data;
    }

    public List<PictureAPIResponse> getEmployee_picture() {
        return employee_picture;
    }

    public void setEmployee_picture(List<PictureAPIResponse> employee_picture) {
        this.employee_picture = employee_picture;
    }
}
