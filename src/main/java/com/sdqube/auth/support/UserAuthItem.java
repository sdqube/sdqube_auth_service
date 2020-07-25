package com.sdqube.auth.support;

import com.sdqube.service.utils.SDQubeDate;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/26/20 3:19 AM
 */
public class UserAuthItem {
    String username;
    String email;

    private Boolean deleted = false;
    private Long deleted_at = SDQubeDate.timestamp();
    private Long created_at = SDQubeDate.timestamp();
    private Long updated_at = SDQubeDate.timestamp();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Long deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Long updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "UserAuthItem{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", deleted=" + deleted +
                ", deleted_at=" + deleted_at +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
