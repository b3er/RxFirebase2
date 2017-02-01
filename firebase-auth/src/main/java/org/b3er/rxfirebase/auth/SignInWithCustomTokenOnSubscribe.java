package org.b3er.rxfirebase.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

final class SignInWithCustomTokenOnSubscribe extends BaseAuthOnSubscribe {

  private final String token;

  SignInWithCustomTokenOnSubscribe(FirebaseAuth instance, String token) {
    super(instance);
    this.token = token;
  }

  @Override protected Task<AuthResult> createAuthTask(FirebaseAuth instance) {
    return instance.signInWithCustomToken(token);
  }
}
