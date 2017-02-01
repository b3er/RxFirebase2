package org.b3er.rxfirebase.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

final class SignInAnonymouslyOnSubscribe extends BaseAuthOnSubscribe {

  SignInAnonymouslyOnSubscribe(FirebaseAuth instance) {
    super(instance);
  }

  @Override protected Task<AuthResult> createAuthTask(FirebaseAuth instance) {
    return instance.signInAnonymously();
  }
}
