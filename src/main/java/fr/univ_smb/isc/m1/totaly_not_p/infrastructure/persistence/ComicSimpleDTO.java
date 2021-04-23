package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import java.util.Calendar;

public class ComicSimpleDTO {
    private Long id;
    private String title;
    private Calendar date;
    private Integer subNb;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Integer getSubNb() {
        return subNb;
    }

    public void setSubNb(Integer subNb) {
        this.subNb = subNb;
    } 
}
