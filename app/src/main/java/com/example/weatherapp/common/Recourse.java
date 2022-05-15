package com.example.weatherapp.common;

import androidx.annotation.Nullable;

public class Recourse<T> {
    public final Status status;
    public final T data;
    public final String msg;

    public Recourse(Status status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public  static  <T> Recourse<T> success (T data){
        return  new Recourse<>(Status.SUCCESS,data,null);
    }
    public  static  <T> Recourse<T> error (String msg ,@Nullable T data){
        return  new Recourse<>(Status.ERROR,data,msg);
    }
    public  static  <T> Recourse<T> loading (){
        return  new Recourse<>(Status.LOADING,null,null);
    }
}
