package com.tutor93.tugasfrensky;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frensky on 7/28/15.
 */
public class EmployeeModel {
    private Context ctx;
    private static DatabaseHelper dbHelper;
    protected Dao<EmployeEntity, ?> dao;

    public EmployeeModel(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;

        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(ctx);
        }

        try {
            if (dao == null) {
                dao = (Dao<EmployeEntity, ?>) dbHelper.getDao(EmployeEntity.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEmployee(Employee employeeModel){
        EmployeEntity employe = new EmployeEntity();
        employe.setName(employeeModel.getName());
        employe.setNotes(employeeModel.getNotes());
        employe.setAge(employeeModel.getAge());
        employe.setAvatar(employeeModel.getAvatar());
        employe.setBookmark(employeeModel.isBookmark());
        employe.setIs_male(employeeModel.is_male());
        employe.setJobs(employeeModel.getJobs());
        employe.setJoin(employeeModel.getJoin());

        this.upsertToDatabase(employe);
    }


    public void updateOrder(Employee employee){

        EmployeEntity employeEntity = new EmployeEntity();

        int Id = employee.getId();

        employeEntity = getEmployeeById(Id);

        if(employeEntity != null){
            employeEntity.setName(employee.getName());
            employeEntity.setNotes(employee.getNotes());
            employeEntity.setAge(employee.getAge());
            employeEntity.setAvatar(employee.getAvatar());
            employeEntity.setBookmark(employee.isBookmark());
            employeEntity.setIs_male(employee.is_male());
            employeEntity.setJobs(employee.getJobs());
            employeEntity.setJoin(employee.getJoin());
            this.upsertToDatabase(employeEntity);
        }

    }

    private void upsertToDatabase(EmployeEntity employee) {
        try {
            dao.createOrUpdate(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(EmployeEntity employee) {
        try {
            dao.createIfNotExists(employee);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderData(EmployeEntity employee) {
        try {
            dao.delete(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public EmployeEntity getEmployeeById(int employeId) {
        List<EmployeEntity> data = new ArrayList<EmployeEntity>();
        try {
            data = dao.queryBuilder().where().eq(EmployeEntity.ID, employeId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    public List<EmployeEntity> getAllEmployee() {
        List<EmployeEntity> data = new ArrayList<EmployeEntity>();
        try {
            data = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }


}