package com.jnzydzx.control.face;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.*;

@Named
@RequestScoped
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmployeeConstruction {
    private String userName;
    private String userId;
    private String passWord;

    public String save() {
        System.out.println("Successful: " + this.userName);
        return "success";
    }
}
