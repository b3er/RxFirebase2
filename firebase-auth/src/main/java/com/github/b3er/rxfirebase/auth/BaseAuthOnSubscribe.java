package com.github.b3er.rxfirebase.auth;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

abstract class BaseAuthOnSubscribe implements SingleOnSubscribe<FirebaseUser> {

  private final FirebaseAuth instance;

  BaseAuthOnSubscribe(FirebaseAuth instance) {
    this.instance = instance;
  }

  protected abstract Task<AuthResult> createAuthTask(FirebaseAuth instance);

  @Override public void subscribe(final SingleEmitter<FirebaseUser> emitter) {
    final OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
      @Override public void onComplete(@NonNull Task<AuthResult> task) {
        if (!task.isSuccessful()) {
          if (!emitter.isDisposed()) {
            emitter.onError(task.getException());
          }
          return;
        }

        if (!emitter.isDisposed()) {
          emitter.onSuccess(task.getResult().getUser());
        }
      }
    };
    createAuthTask(instance).addOnCompleteListener(listener);
  }
}
