package ru.kroshchenko.ruven.portal;

import ru.kroshchenko.ruven.dao.DepartmentDao;
import ru.kroshchenko.ruven.dao.EmployeeDao;
import ru.kroshchenko.ruven.entities.Department;
import ru.kroshchenko.ruven.entities.Employee;
import ru.kroshchenko.ruven.entities.User;
import ru.kroshchenko.ruven.portal.dao.LoginDAO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

@ManagedBean
@SessionScoped
public class PortalAuthenticationBean implements Serializable {

    public static final String AUTH_KEY = "portal_employee";
    public static final String DEPARTMENT_KEY = "portal_company";
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(AUTH_KEY) != null;
    }

    public String login() {
        String errorMessage;
        User user;
        if (email != null && password != null && email.length() > 4 && password.trim().length() > 5) {
            user = LoginDAO.validate(email, password);
            if (user != null) {
                Employee employee = EmployeeDao.get(user.getEmployeeId());
                Department department = DepartmentDao.get(employee.getDepartmentId());
                if (department != null) {
                    Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
                    sessionMap.put(AUTH_KEY, employee);
                    sessionMap.put(DEPARTMENT_KEY, department);
                    return "restricted/portal?faces-redirect=true";
                } else {
                    errorMessage = "...";
                }
            } else {
                errorMessage = "Wrong email or password";
            }
        } else {
            errorMessage = "Email or password is too short";
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, errorMessage, null));
        return "";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(AUTH_KEY);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(DEPARTMENT_KEY);
        return "login";
    }
}