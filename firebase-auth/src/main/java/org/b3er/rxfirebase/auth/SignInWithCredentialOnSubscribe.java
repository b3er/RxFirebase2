package org.b3er.rxfirebase.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

final class SignInWithCredentialOnSubscribe extends BaseAuthOnSubscribe {

  private final AuthCredential credential;

  SignInWithCredentialOnSubscribe(FirebaseAuth instance, AuthCredential credential) {
    super(instance);
    this.credential = credential;
  }

  @Override protected Task<AuthResult> createAuthTask(FirebaseAuth instance) {
    return instance.signInWithCredential(credential);
  }
}
