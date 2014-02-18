package com.company.springjpa.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Abstract entity containing version and last modified properties
 * 
 * @author Joris Aper
 * 
 * @param <ID>
 *            the id type
 * @param <VERSION>
 *            the version type
 */
@MappedSuperclass
public abstract class AbstractEntity<ID, VERSION> {

    @Id
    @GeneratedValue
    private ID id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Version
    private VERSION version;

    public ID getId() {
        return id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public VERSION getVersion() {
        return version;
    }

    @PreUpdate
    @PrePersist
    private void updateTimestamp() {
        lastModified = new Date();
    }
}
