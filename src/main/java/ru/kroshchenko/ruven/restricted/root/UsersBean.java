package ru.kroshchenko.ruven.restricted.root;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kroshchenko.ruven.dao.RoleDao;
import ru.kroshchenko.ruven.dao.UserDao;
import ru.kroshchenko.ruven.entities.RoleName;
import ru.kroshchenko.ruven.entities.User;
import ru.kroshchenko.ruven.utils.ClassNameUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksandr Streltsov
 *         2016.01.25
 */
@ManagedBean(name = "users")
@SessionScoped
public class UsersBean implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public String idToRemove = "";
    public String email;
    public String password;
    public String currentRole;
    private Map<String, String> roleValues = new HashMap<>();

    public UsersBean() {
        for (RoleName roleName : RoleName.values()) {
            roleValues.put(roleName.name(), roleName.name());
        }
    }

    public void createUser() {
        logger.info(String.format("create user [%s, %s]", email, password));
        String errorMessage = null;
        if (checkEmail()) {
            if (checkPassword()) {
                RoleName roleName = RoleName.valueOf(currentRole);
                User user = UserDao.save(new User(null, RoleDao.get(roleName), email, password));
                if (user == null) {
                    errorMessage = String.format("Пользователь %s не создан", email);
                }
            } else {
                errorMessage = "Проверь пароль. Мин-я длина 8, макс-я - 16";
            }
        } else {
            errorMessage = "Проверь email, не правильный.";
        }
        if (errorMessage != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, errorMessage, null));
            return;
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private boolean checkEmail() {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    private boolean checkPassword() {
        return password != null && password.trim().length() > 7 && password.trim().length() < 17;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdToRemove() {
        return idToRemove;
    }

    public void setIdToRemove(String idToRemove) {
        this.idToRemove = idToRemove;
    }

    public Map<String, String> getRoleValues() {
        return roleValues;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public String getCurrentRole() {
        return currentRole;
    }
}
