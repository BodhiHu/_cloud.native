package BodhiTree.tree.lib.auth;

import java.io.Serializable;

public class JwtToken implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return this.token;
    }
}

