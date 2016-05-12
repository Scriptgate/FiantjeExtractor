package net.scriptgate.fiantje.http;

import net.scriptgate.http.FormService;

class FiantjeFormService extends FormService {

    private static final String LOGIN_FORM_ID = "login-form";
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "userpass";

    @Override
    protected String getFormId() {
        return LOGIN_FORM_ID;
    }

    @Override
    protected String getUsernameField() {
        return USERNAME_FIELD;
    }

    @Override
    protected String getPasswordField() {
        return PASSWORD_FIELD;
    }
}
