package org.b3er.rxfirebase.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

class CreateUserWithEmailAndPasswordOnSubscribe extends BaseAuthOnSubscribe {

  private final String email;

  private final String password;

  CreateUserWithEmailAndPasswordOnSubscribe(FirebaseAuth instance, String email, String password) {
    super(instance);
    this.email = email;
    this.password = password;
  }

  @Override protected Task<AuthResult> createAuthTask(FirebaseAuth instance) {
    return instance.createUserWithEmailAndPassword(email, password);
  }
}
