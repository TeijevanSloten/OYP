/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ordina.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tsl20897
 */
@Entity
@Table(name = "attachments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachments.findAll", query = "SELECT a FROM Attachments a"),
    @NamedQuery(name = "Attachments.findById", query = "SELECT a FROM Attachments a WHERE a.id = :id"),
    @NamedQuery(name = "Attachments.findByEmailid", query = "SELECT a FROM Attachments a WHERE a.emailid = :emailid")})
public class Attachments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "filename")
    private String filename;
    @Lob
    @Size(max = 65535)
    @Column(name = "filesize")
    private String filesize;
    @Lob
    @Size(max = 65535)
    @Column(name = "contenttype")
    private String contenttype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "emailid")
    private int emailid;

    public Attachments() {
    }

    public Attachments(Integer id) {
        this.id = id;
    }

    public Attachments(Integer id, String filename, int emailid) {
        this.id = id;
        this.filename = filename;
        this.emailid = emailid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public int getEmailid() {
        return emailid;
    }

    public void setEmailid(int emailid) {
        this.emailid = emailid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachments)) {
            return false;
        }
        Attachments other = (Attachments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ordina.entity.Attachments[ id=" + id + " ]";
    }
    
}
