package com.github.b3er.rxfirebase.auth;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import java.util.List;

final class FetchProvidersForEmailOnSubscribe implements MaybeOnSubscribe<List<String>> {

  private final FirebaseAuth instance;

  private final String email;

  FetchProvidersForEmailOnSubscribe(FirebaseAuth instance, String email) {
    this.instance = instance;
    this.email = email;
  }

  @Override public void subscribe(final MaybeEmitter<List<String>> emitter) {

    final OnCompleteListener<ProviderQueryResult> listener =
        new OnCompleteListener<ProviderQueryResult>() {
          @Override public void onComplete(@NonNull Task<ProviderQueryResult> task) {
            if (!task.isSuccessful()) {
              if (!emitter.isDisposed()) {
                emitter.onError(task.getException());
              }
              return;
            }

            if (!emitter.isDisposed()) {
              ProviderQueryResult result = task.getResult();
              if (result != null) {
                List<String> providers = result.getProviders();
                if (providers != null) {
                  emitter.onSuccess(providers);
                  return;
                }
              }
              emitter.onComplete();
            }
          }
        };

    instance.fetchProvidersForEmail(email).addOnCompleteListener(listener);
  }
}
