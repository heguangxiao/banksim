/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.htcjsc.web.banksim.model.ext.AngularModel;
import vn.htcjsc.web.banksim.model.ext.ResponResult;

/**
 *
 * @author Private
 * @param <T>
 */
public interface TempControllerIF<T> {

    public String list(ModelMap modelMap, HttpServletRequest request, RedirectAttributes redrAtt);

    public ResponseEntity<AngularModel<T>> getData(HttpServletRequest request);

    public String createView(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt);

    public String createProcess(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt);

    public String editView(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt);

    public String editProcess(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt);

    public ResponseEntity<AngularModel<ResponResult>> delete(HttpServletRequest request);
}
