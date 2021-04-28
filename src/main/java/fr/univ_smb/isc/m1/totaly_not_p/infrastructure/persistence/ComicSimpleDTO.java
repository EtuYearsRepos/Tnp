package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

public class ComicSimpleDTO {
    private Long id;
    private String title;
    private String publicationDate;
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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getSubNb() {
        return subNb;
    }

    public void setSubNb(Integer subNb) {
        this.subNb = subNb;
    } 
}
