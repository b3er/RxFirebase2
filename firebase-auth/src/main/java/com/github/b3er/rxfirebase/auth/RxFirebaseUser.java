package com.github.b3er.rxfirebase.auth;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import io.reactivex.Completable;
import io.reactivex.Single;

public final class RxFirebaseUser {
  private RxFirebaseUser() {
    throw new AssertionError("No instances");
  }

  /**
   * @see FirebaseUser#delete()
   */
  @CheckResult @NonNull public static Completable delete(@NonNull FirebaseUser user) {
    return Completable.create(new UserDeleteOnSubscribe(user));
  }

  /**
   * @see FirebaseUser#getToken(boolean)
   */
  @CheckResult @NonNull public static Single<String> getToken(@NonNull FirebaseUser user,
      boolean forceRefresh) {
    return Single.create(new UserGetTokenOnSubscribe(user, forceRefresh));
  }

  /**
   * @see FirebaseUser#linkWithCredential(AuthCredential)
   */
  @CheckResult @NonNull public static Single<AuthResult> linkWithCredential(
      @NonNull FirebaseUser user, @NonNull AuthCredential credential) {
    return Single.create(new UserLinkWithCredentialOnSubscribe(user, credential));
  }

  /**
   * @see FirebaseUser#reauthenticate(AuthCredential)
   */
  @CheckResult @NonNull public static Completable reauthenticate(@NonNull FirebaseUser user,
      @NonNull AuthCredential credential) {
    return Completable.create(new UserReauthenticateOnSubscribe(user, credential));
  }

  /**
   * @see FirebaseUser#reload()
   */
  @CheckResult @NonNull public static Completable reload(@NonNull FirebaseUser user) {
    return Completable.create(new UserReloadOnSubscribe(user));
  }

  /**
   * @see FirebaseUser#sendEmailVerification()
   */
  @CheckResult @NonNull public static Completable sendEmailVerification(
      @NonNull FirebaseUser user) {
    return Completable.create(new UserSendEmailVerificationOnSubscribe(user));
  }

  /**
   * @see FirebaseUser#unlink(String)
   */
  @CheckResult @NonNull public static Single<AuthResult> unlink(@NonNull FirebaseUser user,
      @NonNull String provider) {
    return Single.create(new UserUnlinkOnSubscribe(user, provider));
  }

  /**
   * @see FirebaseUser#updateEmail(String)
   */
  @CheckResult @NonNull public static Completable updateEmail(@NonNull FirebaseUser user,
      @NonNull String email) {
    return Completable.create(new UserUpdateEmailOnSubscribe(user, email));
  }

  /**
   * @see FirebaseUser#updatePassword(String)
   */
  @CheckResult @NonNull public static Completable updatePassword(@NonNull FirebaseUser user,
      @NonNull String password) {
    return Completable.create(new UserUpdatePasswordOnSubscribe(user, password));
  }

  /**
   * @see FirebaseUser#updateProfile(UserProfileChangeRequest)
   */
  @CheckResult @NonNull public static Completable updateProfile(@NonNull FirebaseUser user,
      @NonNull UserProfileChangeRequest request) {
    return Completable.create(new UserUpdateProfileOnSubscribe(user, request));
  }
}
