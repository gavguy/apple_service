
package apple.service.core.auth.control;

import apple.service.core.auth.model.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Password;
import java.util.Set;
import java.util.HashSet;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;


import static apple.service.core.auth.model.Role.*;

@ApplicationScoped
public class JpaIdentityStore implements IdentityStore {

    @Inject
    private PasswordHashIng passwordHash;

    @Inject
    private UserDAO userDAO;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        String caller = credential.getCaller();
        return userDAO.findUser(caller)
                .filter(u -> checkPassword(u.getPasswordHash(),
                        credential.getPassword()))
                .map(u -> new CredentialValidationResult(u.getLoginName(),
                        makeRoles(u.getRoleName())))
                .orElse(CredentialValidationResult
                        .INVALID_RESULT);
    }


    //
    private Set<String> makeRoles(Role role) {
        Set<String> result = new HashSet<>(4, 1);
        switch (role) {
            case ADMIN:
                result.add(ADMIN.toString());
            case MANAGER:
                result.add(MANAGER.toString());
            case USER:
                result.add(USER.toString());
        }
        return result;
    }

    //


    private boolean checkPassword(String hashedPassword,
                                  Password password) {
        return passwordHash
                .get()
                .verify(password.getValue(),
                        hashedPassword);
    }
}
