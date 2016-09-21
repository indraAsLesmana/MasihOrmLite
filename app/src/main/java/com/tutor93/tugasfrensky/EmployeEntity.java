package com.tutor93.tugasfrensky;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by frensky on 7/28/15.
 */
public class EmployeEntity implements Serializable {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String JOBS = "jobs";
    public static final String AGE = "age";
    public static final String JOINDATE = "joinDate";
    public static final String ISMALE = "is_nmale";
    public static final String NOTES = "notes";
    public static final String BOOKMARK = "isBookMark";
    public static final String AVATAR = "avatar";


    /*
    * jika pake id = true, error.
    * solved with, links: http://stackoverflow.com/questions/29894963/getdao-exception-with-generatedid-true
    * */
    @DatabaseField(columnName = ID, generatedId = true, allowGeneratedIdInsert = true)
    protected int id;
    @DatabaseField(columnName = NAME)
    protected String name;
    @DatabaseField(columnName = JOBS)
    protected String jobs;
    @DatabaseField(columnName = AGE)
    protected int age;
    @DatabaseField(columnName = ISMALE)
    protected boolean is_male;
    @DatabaseField(columnName = JOINDATE)
    protected String join;
    @DatabaseField(columnName = NOTES)
    protected String notes;
    @DatabaseField(columnName = BOOKMARK)
    protected boolean bookmark;
    @DatabaseField(columnName = AVATAR)
    protected String avatar;

    public EmployeEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean is_male() {
        return is_male;
    }

    public void setIs_male(boolean is_male) {
        this.is_male = is_male;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}