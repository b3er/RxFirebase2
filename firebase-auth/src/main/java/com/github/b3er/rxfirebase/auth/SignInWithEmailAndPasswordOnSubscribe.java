package com.github.b3er.rxfirebase.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

final class SignInWithEmailAndPasswordOnSubscribe extends BaseAuthOnSubscribe {

  private final String email;

  private final String password;

  SignInWithEmailAndPasswordOnSubscribe(FirebaseAuth instance, String email, String password) {
    super(instance);
    this.email = email;
    this.password = password;
  }

  @Override protected Task<AuthResult> createAuthTask(FirebaseAuth instance) {
    return instance.signInWithEmailAndPassword(email, password);
  }
}
