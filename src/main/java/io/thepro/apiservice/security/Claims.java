package io.thepro.apiservice.security;

import java.util.UUID;

import com.google.firebase.auth.*;
import io.thepro.apiservice.controllers.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Claims {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    public Claims() {

    }

    //https://firebase.google.com/docs/auth/admin/custom-claims
    public void setClaims() {
        try {
            // Set admin privilege on the user corresponding to uid.
            Map<String, Object> claims = new HashMap<>();
            claims.put("admin", true);
            FirebaseAuth.getInstance().setCustomUserClaims("uid", claims);
            // The new custom claims will propagate to the user's ID token the
            // next time a new one is issued.
        } catch (FirebaseAuthException e){
            //handle me
        }
    }

    public void doStuffIfHaveClaims() {
        try{
            // Verify the ID token first.
            FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken("idToken");
            if (Boolean.TRUE.equals(decoded.getClaims().get("admin"))) {
                // Allow access to requested admin resource.
            }
        } catch (FirebaseAuthException e) {
            //handle this
        }
    }

    public void getClaims() {
        try{
            // Lookup the user associated with the specified uid.
            UserRecord user = FirebaseAuth.getInstance().getUser("uid");
            System.out.println(user.getCustomClaims().get("admin"));
        } catch (FirebaseAuthException e){
            //handle this too
        }
    }

    public void createUser(String displayName, String email) throws FirebaseAuthException {
        FirebaseAuth.getInstance().createUser(
                new UserRecord.CreateRequest()
                        .setDisplayName(displayName)
                        .setEmail(email)
                        .setUid(generateId())
        );

    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }

    public UserRecord.UpdateRequest updateUserClaims(String uid) throws FirebaseAuthException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("admin", true);
        //Calling this method with a null argument removes any custom claims from the user account.
        return new UserRecord.UpdateRequest(uid).setCustomClaims(claims);
    }

    public ImportUserRecord addClaimsToFirebaseUser(Map<String, Object> claims, String uid) {
        return ImportUserRecord.builder()
                .setUid(uid)
                .putAllCustomClaims(claims)
                .build();
    }

    public void importUsersToFirebase(List<ImportUserRecord> users) throws FirebaseAuthException {
        UserImportResult result = FirebaseAuth.getInstance().importUsers(users);
        LOGGER.debug(getErrorsFromUserImportResult(result));
    }

    public String getErrorsFromUserImportResult(UserImportResult result) {
        StringBuilder errorMessage = new StringBuilder("User Import Result: ");
        if(result.getFailureCount()>0){
            List<ErrorInfo> errors = result.getErrors();
            for(ErrorInfo error : errors){
                errorMessage.append(" Error: " + error.getReason() + "at: " + error.getIndex() + "\n");
            }
            return errorMessage.toString();
        } else {
            return " No Errors - Success!";
        }
    }

}
