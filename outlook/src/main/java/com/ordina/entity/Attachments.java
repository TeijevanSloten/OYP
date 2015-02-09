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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "filename")
    private String filename;
    @Lob
    @Size(max = 65535)
    @Column(name = "fieldname")
    private String fieldname;
    @Lob
    @Size(max = 65535)
    @Column(name = "filesize")
    private String filesize;
    @Lob
    @Size(max = 65535)
    @Column(name = "contenttype")
    private String contenttype;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "emailid")
    private Integer emailid;
    @JoinColumn(name = "emailid", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Email email;

    public Attachments() {
    }

    public Attachments(Integer emailid) {
        this.emailid = emailid;
    }

    public Attachments(Integer emailid, int id, String filename) {
        this.emailid = emailid;
        this.id = id;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
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

    public Integer getEmailid() {
        return emailid;
    }

    public void setEmailid(Integer emailid) {
        this.emailid = emailid;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailid != null ? emailid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachments)) {
            return false;
        }
        Attachments other = (Attachments) object;
        if ((this.emailid == null && other.emailid != null) || (this.emailid != null && !this.emailid.equals(other.emailid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ordina.entity.Attachments[ emailid=" + emailid + " ]";
    }
    
}
