package apple.service.core.auth.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.PasswordHash;

@ApplicationScoped
public class PasswordHashIng {
    @Inject
    private PasswordHash passwordHash;

    public PasswordHash get() {
        return passwordHash;
    }
}
