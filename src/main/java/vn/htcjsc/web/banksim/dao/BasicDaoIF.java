/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.dao;

/**
 *
 * @author tuanp
 * @param <T>
 */
public interface BasicDaoIF<T> {

    public T findById(int id);

    public int create(T one);

    public T update(T one);

    public T delete(int id);

}
