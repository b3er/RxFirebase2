package com.github.b3er.rxfirebase.auth;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.memoizrlabs.retrooptional.Optional;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

public final class RxFirebaseAuth {
  private RxFirebaseAuth() {
    throw new AssertionError("No instances");
  }

  /**
   * @see FirebaseAuth#addAuthStateListener(FirebaseAuth.AuthStateListener)
   */
  @CheckResult @NonNull public static Observable<FirebaseAuth> authStateChanges(
      @NonNull FirebaseAuth instance) {
    return Observable.create(new AuthStateChangesOnSubscribe(instance));
  }

  /**
   * @see FirebaseAuth#createUserWithEmailAndPassword(String, String)
   */
  @CheckResult @NonNull public static Single<FirebaseUser> createUserWithEmailAndPassword(
      @NonNull FirebaseAuth instance, @NonNull String email, @NonNull String password) {
    return Single.create(new CreateUserWithEmailAndPasswordOnSubscribe(instance, email, password));
  }

  /**
   * @see FirebaseAuth#fetchProvidersForEmail(String)
   */
  @CheckResult @NonNull public static Single<Optional<List<String>>> fetchProvidersForEmail(
      @NonNull FirebaseAuth instance, @NonNull String email) {
    return Single.create(new FetchProvidersForEmailOnSubscribe(instance, email));
  }

  /**
   * @see FirebaseAuth#getCurrentUser()
   */
  @CheckResult @NonNull public static Single<Optional<FirebaseUser>> getCurrentUser(
      @NonNull final FirebaseAuth instance) {
    return Single.create(new GetCurrentUserOnSubscribe(instance));
  }

  /**
   * @see FirebaseAuth#sendPasswordResetEmail(String)
   */
  @CheckResult @NonNull public static Completable sendPasswordResetEmail(
      @NonNull FirebaseAuth instance, @NonNull String email) {
    return Completable.create(new SendPasswordResetEmailOnSubscribe(instance, email));
  }

  /**
   * @see FirebaseAuth#signInAnonymously()
   */
  @CheckResult @NonNull public static Single<FirebaseUser> signInAnonymously(
      @NonNull FirebaseAuth instance) {
    return Single.create(new SignInAnonymouslyOnSubscribe(instance));
  }

  /**
   * @see FirebaseAuth#signInWithCredential(AuthCredential)
   */
  @CheckResult @NonNull public static Single<FirebaseUser> signInWithCredential(
      @NonNull FirebaseAuth instance, @NonNull AuthCredential credential) {
    return Single.create(new SignInWithCredentialOnSubscribe(instance, credential));
  }

  /**
   * @see FirebaseAuth#signInWithCustomToken(String)
   */
  @CheckResult @NonNull public static Single<FirebaseUser> signInWithCustomToken(
      @NonNull FirebaseAuth instance, @NonNull String token) {
    return Single.create(new SignInWithCustomTokenOnSubscribe(instance, token));
  }

  /**
   * @see FirebaseAuth#signInWithEmailAndPassword(String, String)
   */
  @CheckResult @NonNull public static Single<FirebaseUser> signInWithEmailAndPassword(
      @NonNull FirebaseAuth instance, @NonNull String email, @NonNull String password) {
    return Single.create(new SignInWithEmailAndPasswordOnSubscribe(instance, email, password));
  }

  /**
   * @see FirebaseAuth#signOut()
   */
  @CheckResult @NonNull public static Completable signOut(@NonNull FirebaseAuth instance) {
    return Completable.create(new SignOutOnSubscribe(instance));
  }

  /**
   * @see FirebaseAuth#applyActionCode(String)
   */
  @CheckResult @NonNull public static Completable applyActionCode(@NonNull FirebaseAuth instance,
      @NonNull String code) {
    return Completable.create(new ApplyActionCodeOnSubscribe(instance, code));
  }

  /**
   * @see FirebaseAuth#checkActionCode(String)
   */
  @CheckResult @NonNull public static Single<ActionCodeResult> checkActionCode(
      @NonNull FirebaseAuth instance, @NonNull String code) {
    return Single.create(new CheckActionCodeOnSubscribe(instance, code));
  }

  /**
   * @see FirebaseAuth#confirmPasswordReset(String, String)
   */
  @CheckResult @NonNull public static Completable confirmPasswordReset(
      @NonNull FirebaseAuth instance, @NonNull String code, @NonNull String newPassword) {
    return Completable.create(new ConfirmPasswordResetOnSubscribe(instance, code, newPassword));
  }

  /**
   * @see FirebaseAuth#verifyPasswordResetCode(String)
   */
  @CheckResult @NonNull public static Single<String> verifyPasswordResetCode(
      @NonNull FirebaseAuth instance, @NonNull String code) {
    return Single.create(new VerifyPasswordResetCodeOnSubscribe(instance, code));
  }
}
