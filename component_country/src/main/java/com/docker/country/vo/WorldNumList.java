package com.docker.country.vo;

import com.docker.country.widget.recycleIndex.IndexableEntity;
import com.docker.country.widget.recycleIndex.PinyinUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class WorldNumList implements Serializable {

    public ArrayList<WorldEnty> A = new ArrayList<>();
    public ArrayList<WorldEnty> B = new ArrayList<>();
    public ArrayList<WorldEnty> C = new ArrayList<>();
    public ArrayList<WorldEnty> D = new ArrayList<>();
    public ArrayList<WorldEnty> E = new ArrayList<>();
    public ArrayList<WorldEnty> F = new ArrayList<>();
    public ArrayList<WorldEnty> G = new ArrayList<>();
    public ArrayList<WorldEnty> H = new ArrayList<>();
    public ArrayList<WorldEnty> I = new ArrayList<>();
    public ArrayList<WorldEnty> J = new ArrayList<>();
    public ArrayList<WorldEnty> K = new ArrayList<>();
    public ArrayList<WorldEnty> L = new ArrayList<>();
    public ArrayList<WorldEnty> M = new ArrayList<>();
    public ArrayList<WorldEnty> N = new ArrayList<>();
    public ArrayList<WorldEnty> O = new ArrayList<>();
    public ArrayList<WorldEnty> P = new ArrayList<>();
    public ArrayList<WorldEnty> Q = new ArrayList<>();
    public ArrayList<WorldEnty> R = new ArrayList<>();
    public ArrayList<WorldEnty> S = new ArrayList<>();
    public ArrayList<WorldEnty> T = new ArrayList<>();
    public ArrayList<WorldEnty> U = new ArrayList<>();
    public ArrayList<WorldEnty> V = new ArrayList<>();
    public ArrayList<WorldEnty> W = new ArrayList<>();
    public ArrayList<WorldEnty> X = new ArrayList<>();
    public ArrayList<WorldEnty> Y = new ArrayList<>();
    public ArrayList<WorldEnty> Z = new ArrayList<>();

    public static class WorldEnty implements Serializable, IndexableEntity {

        public String name;
        public String num;
        public String pinyin;
        public String id;
        public String grade;
        public String spelling;
        public String curtype;
        public String global_num;
        public String cityCode;

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getGlobal_num() {
            return global_num;
        }

        public void setGlobal_num(String global_num) {
            this.global_num = global_num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public boolean isCheck = true;

        public boolean isSubscribe = false;

        @Override
        public String getFieldIndexBy() {
            return name;
        }

        @Override
        public void setFieldIndexBy(String indexField) {
            this.name = indexField;
        }

        @Override
        public void setFieldPinyinIndexBy(String pinyin) {
            // 需要用到拼音时(比如:搜索), 可增添pinyin字段 this.pinyin  = pinyin
            // 见 CityEntity
            this.pinyin = PinyinUtil.getPingYin(this.name);
        }
    }

}
