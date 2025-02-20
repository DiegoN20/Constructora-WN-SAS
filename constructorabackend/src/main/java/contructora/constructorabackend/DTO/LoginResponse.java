package contructora.constructorabackend.DTO;

public class LoginResponse {

    private String jwt;

    public LoginResponse(String jwtToken) {
        this.jwt = jwtToken;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
