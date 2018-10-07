package com.project.hdl.generator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreenDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "com.project.hdl.salesap.model");
        general(schema);
        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    private static void general(Schema schema) {
        //Entity : AM_USER
        Entity user = schema.addEntity("User").addImport("com.google.gson.annotations.SerializedName");
        user.setTableName("AM_USER");
        user.addStringProperty("login_id").notNull().primaryKey().codeBeforeField("@SerializedName(\"login_id\")");
        user.addStringProperty("job").codeBeforeField("@SerializedName(\"job\")");
        user.addStringProperty("office").codeBeforeField("@SerializedName(\"office\")");
        user.addStringProperty("branch").codeBeforeField("@SerializedName(\"branch\")");
        user.addStringProperty("target").codeBeforeField("@SerializedName(\"target\")");
        user.addStringProperty("current_gain").codeBeforeField("@SerializedName(\"current_gain\")");
        user.addStringProperty("full_name").codeBeforeField("@SerializedName(\"full_name\")");

        //Entity : TR_TASK
        Entity task = schema.addEntity("Task").addImport("com.google.gson.annotations.SerializedName");
        task.setTableName("TR_TASK");
        task.addStringProperty("uid_task").notNull().primaryKey().codeBeforeField("@SerializedName(\"uid_task\")");
        task.addStringProperty("login_id").codeBeforeField("@SerializedName(\"login_id\")");
        task.addStringProperty("cust_name").codeBeforeField("@SerializedName(\"cust_name\")");
        task.addStringProperty("dest_address").codeBeforeField("@SerializedName(\"dest_address\")");
        task.addStringProperty("dtm_assign").codeBeforeField("@SerializedName(\"dtm_assign\")");
        task.addStringProperty("lon").codeBeforeField("@SerializedName(\"lon\")");
        task.addStringProperty("lat").codeBeforeField("@SerializedName(\"lat\")");
        task.addStringProperty("phone_numb").codeBeforeField("@SerializedName(\"phone_numb\")");
        task.addStringProperty("status").codeBeforeField("@SerializedName(\"status\")");
        task.addDateProperty("dtm_upd").codeBeforeField("@SerializedName(\"dtm_upd\")");
        task.addDateProperty("dtm_assign_tmp").codeBeforeField("@SerializedName(\"dtm_assign_tmp\")");

        //Entity : TR_REPORT_HEADER
        Entity reportH = schema.addEntity("ReportH").addImport("com.google.gson.annotations.SerializedName");
        reportH.setTableName("TR_REPORT_H");
        reportH.addStringProperty("uid_report_h").notNull().primaryKey().codeBeforeField("@SerializedName(\"uid_report_h\")");
        reportH.addStringProperty("uid_task").codeBeforeField("@SerializedName(\"uid_task\")");
        reportH.addStringProperty("dtm_submit").codeBeforeField("@SerializedName(\"dtm_submit\")");
        reportH.addStringProperty("total_price").codeBeforeField("@SerializedName(\"total_price\")");
        reportH.addStringProperty("total_count").codeBeforeField("@SerializedName(\"total_count\")");

        //Entity : TR_REPORT_DETAIL
        Entity reportD = schema.addEntity("ReportD").addImport("com.google.gson.annotations.SerializedName");
        reportD.setTableName("TR_REPORT_D");
        reportD.addStringProperty("uid_report_d").notNull().primaryKey().codeBeforeField("@SerializedName(\"uid_report_d\")");
        reportD.addStringProperty("uid_report_h").codeBeforeField("@SerializedName(\"uid_report_h\")");
        reportD.addStringProperty("product_name").codeBeforeField("@SerializedName(\"product_name\")");
        reportD.addStringProperty("product_count").codeBeforeField("@SerializedName(\"product_count\")");
        reportD.addStringProperty("product_price").codeBeforeField("@SerializedName(\"product_price\")");
    }
}
