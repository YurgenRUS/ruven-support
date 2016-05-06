package ru.kroshchenko.ruven.restricted.root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kroshchenko.ruven.config.ContextProperties;
import ru.kroshchenko.ruven.utils.ClassNameUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name = "rootAuthenticationBean", eager = true)
@SessionScoped
public class RootAuthenticationBean implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static final String AUTH_KEY = "root";
    public Boolean showAll;

    private String login;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String email) {
        this.login = email;
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(AUTH_KEY) != null;
    }

    public String login() {
        String errorMessage;
        if (checkLogin()) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                    .put(AUTH_KEY, true);
            showAll = true;
            return "restricted/root?faces-redirect=true";
        } else {
            errorMessage = "Wrong login or password";
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, errorMessage, null));
        return "";
    }

    private boolean checkLogin() {
        String login = ContextProperties.getProperty(ContextProperties.ContextProperty.ROOT_LOGIN);
        String pwd = ContextProperties.getProperty(ContextProperties.ContextProperty.ROOT_PASSWORD);
        return !(StringUtils.isEmpty(login) || StringUtils.isEmpty(pwd))
                && login.equals(this.login) && pwd.equals(this.password);
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(AUTH_KEY);
        return "login";
    }

    public Boolean getShowAll() {
        return showAll;
    }

    private enum RootProperty {
        LOGIN("ROOT.LOGIN"),
        PASSWORD("ROOT.PASSWORD");

        private String key;

        RootProperty(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}