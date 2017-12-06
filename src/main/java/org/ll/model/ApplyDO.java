package org.ll.model;

import java.io.Serializable;

public class ApplyDO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    private String start;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }


    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    private String image;
    

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ApplyDO [city=" + city + ", time=" + time + ", start=" + start + ", destination=" + destination + ", image="
            + image + "]";
    }

}
