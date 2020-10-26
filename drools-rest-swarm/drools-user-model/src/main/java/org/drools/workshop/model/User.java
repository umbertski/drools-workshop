/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.model;

import lombok.Data;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author salaboy
 */
//@XmlRootElement
@Data
public class User {
    
    private String name;
    private Integer age;
    private String category;

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", age=" + age + ", category=" + category + '}';
    }

   
    
    
}
