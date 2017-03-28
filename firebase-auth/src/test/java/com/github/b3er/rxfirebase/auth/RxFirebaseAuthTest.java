package com.github.b3er.rxfirebase.auth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxFirebaseAuthTest {

  @Mock FirebaseAuth mockFirebaseAuth;

  @Mock AuthCredential mockAuthCredential;

  @Mock AuthResult mockAuthResult;

  @Mock Task<AuthResult> mockAuthResultTask;

  @Mock ActionCodeResult mockActionCodeResult;

  @Mock Task<ActionCodeResult> mockActionCodeResultTask;

  @Mock Task<String> mockStringTask;

  @Mock Task<Void> mockSendPasswordResetEmailTask;

  @Mock Task<ProviderQueryResult> mockFetchProvidersTask;

  @Mock ProviderQueryResult mockProviderQueryResult;

  @Mock FirebaseUser mockFirebaseUser;

  @Mock Task<Void> mockVoidTask;

  private ArgumentCaptor<OnCompleteListener> onComplete;

  private ArgumentCaptor<FirebaseAuth.AuthStateListener> authStateChange;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);

    onComplete = ArgumentCaptor.forClass(OnCompleteListener.class);
    authStateChange = ArgumentCaptor.forClass(FirebaseAuth.AuthStateListener.class);
  }

  @Test public void testAuthStateChanges() {
    TestObserver<FirebaseAuth> obs = TestObserver.create();

    RxFirebaseAuth.authStateChanges(mockFirebaseAuth).subscribe(obs);

    callOnAuthStateChanged();

    obs.assertNotComplete();
    obs.assertValueCount(1);

    obs.dispose();

    callOnAuthStateChanged();

    // Assert no more values are emitted
    obs.assertValueCount(1);
  }

  @Test public void testCreateUserWithEmailAndPassword() {
    when(mockFirebaseUser.getEmail()).thenReturn("foo@bar.com");

    mockSuccessfulAuthResult();

    when(mockFirebaseAuth.createUserWithEmailAndPassword("foo@bar.com", "password")).thenReturn(
        mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.createUserWithEmailAndPassword(mockFirebaseAuth, "foo@bar.com", "password")
        .subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertNoErrors();
    obs.assertComplete();

    obs.assertValue(new Predicate<FirebaseUser>() {
      @Override public boolean test(FirebaseUser firebaseUser) throws Exception {
        return "foo@bar.com".equals(firebaseUser.getEmail());
      }
    });
  }

  @Test public void testCreateUserWithEmailAndPassword_NotSuccessful() {
    when(mockFirebaseUser.getEmail()).thenReturn("foo@bar.com");

    mockNotSuccessfulAuthResult(new IllegalStateException());

    when(mockFirebaseAuth.createUserWithEmailAndPassword("foo@bar.com", "password")).thenReturn(
        mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.createUserWithEmailAndPassword(mockFirebaseAuth, "foo@bar.com", "password")
        .subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testFetchProvidersForEmail() {
    when(mockFirebaseAuth.fetchProvidersForEmail("foo@bar.com")).thenReturn(mockFetchProvidersTask);

    when(mockProviderQueryResult.getProviders()).thenReturn(Arrays.asList("bar.com"));

    mockSuccessfulResultForTask(mockFetchProvidersTask, mockProviderQueryResult);

    when(mockFirebaseAuth.fetchProvidersForEmail("foo@bar.com")).thenReturn(mockFetchProvidersTask);

    TestObserver<List<String>> obs = TestObserver.create();

    RxFirebaseAuth.fetchProvidersForEmail(mockFirebaseAuth, "foo@bar.com").subscribe(obs);

    callOnComplete(mockFetchProvidersTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockFetchProvidersTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(Arrays.asList("bar.com"));
  }

  @Test public void testFetchProvidersForEmail_NotSuccessful() {
    when(mockFirebaseAuth.fetchProvidersForEmail("foo@bar.com")).thenReturn(mockFetchProvidersTask);

    mockNotSuccessfulFetchProvidersResult(new IllegalStateException());

    when(mockFirebaseAuth.fetchProvidersForEmail("foo@bar.com")).thenReturn(mockFetchProvidersTask);

    TestObserver<List<String>> obs = TestObserver.create();

    RxFirebaseAuth.fetchProvidersForEmail(mockFirebaseAuth, "foo@bar.com").subscribe(obs);

    callOnComplete(mockFetchProvidersTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockFetchProvidersTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testGetCurrentUser_notSignedIn() {
    when(mockFirebaseAuth.getCurrentUser()).thenReturn(null);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.getCurrentUser(mockFirebaseAuth).subscribe(obs);

    verify(mockFirebaseAuth).getCurrentUser();

    obs.dispose();

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertNoValues();
  }

  @Test public void testGetCurrentUser_signedIn() {
    when(mockFirebaseUser.getDisplayName()).thenReturn("John Doe");

    when(mockFirebaseAuth.getCurrentUser()).thenReturn(mockFirebaseUser);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.getCurrentUser(mockFirebaseAuth).subscribe(obs);

    verify(mockFirebaseAuth).getCurrentUser();

    obs.dispose();

    obs.assertNoErrors();
    obs.assertComplete();

    obs.assertValue(new Predicate<FirebaseUser>() {
      @Override public boolean test(FirebaseUser firebaseUser) throws Exception {
        return "John Doe".equals(firebaseUser.getDisplayName());
      }
    });
  }

  @Test public void testSendPasswordResetEmail() {
    when(mockFirebaseAuth.sendPasswordResetEmail("email")).thenReturn(
        mockSendPasswordResetEmailTask);

    mockSuccessfulSendPasswordResetEmailResult();

    TestObserver obs = TestObserver.create();

    RxFirebaseAuth.sendPasswordResetEmail(mockFirebaseAuth, "email").subscribe(obs);

    callOnComplete(mockSendPasswordResetEmailTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockSendPasswordResetEmailTask);

    verify(mockFirebaseAuth).sendPasswordResetEmail("email");

    obs.assertNoErrors();

    obs.assertComplete();
  }

  @Test public void testSendPasswordResetEmail_NotSuccessful() {
    when(mockFirebaseAuth.sendPasswordResetEmail("email")).thenReturn(
        mockSendPasswordResetEmailTask);

    mockNotSuccessfulSendPasswordResetEmailResult(new IllegalStateException());

    TestObserver obs = TestObserver.create();

    RxFirebaseAuth.sendPasswordResetEmail(mockFirebaseAuth, "email").subscribe(obs);

    callOnComplete(mockSendPasswordResetEmailTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockSendPasswordResetEmailTask);

    verify(mockFirebaseAuth).sendPasswordResetEmail("email");

    obs.assertError(IllegalStateException.class);
    obs.assertNotComplete();
  }

  @Test public void testSignInAnonymous() {
    when(mockFirebaseUser.isAnonymous()).thenReturn(true);

    mockSuccessfulAuthResult();

    when(mockFirebaseAuth.signInAnonymously()).thenReturn(mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.signInAnonymously(mockFirebaseAuth).subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertNoErrors();

    obs.assertValue(new Predicate<FirebaseUser>() {
      @Override public boolean test(FirebaseUser firebaseUser) throws Exception {
        return firebaseUser.isAnonymous();
      }
    });
  }

  @Test public void testSignInAnonymous_NotSuccessful() {
    mockNotSuccessfulAuthResult(new IllegalStateException());

    when(mockFirebaseAuth.signInAnonymously()).thenReturn(mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.signInAnonymously(mockFirebaseAuth).subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testSignInWithCredential() {
    mockSuccessfulAuthResult();

    when(mockFirebaseAuth.signInWithCredential(mockAuthCredential)).thenReturn(mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.signInWithCredential(mockFirebaseAuth, mockAuthCredential).subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValueCount(1);
  }

  @Test public void testSignInWithCredential_NotSuccessful() {
    mockNotSuccessfulAuthResult(new IllegalStateException());

    when(mockFirebaseAuth.signInWithCredential(mockAuthCredential)).thenReturn(mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.signInWithCredential(mockFirebaseAuth, mockAuthCredential).subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testSignInWithCustomToken() {
    mockSuccessfulAuthResult();

    when(mockFirebaseAuth.signInWithCustomToken("custom_token")).thenReturn(mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.signInWithCustomToken(mockFirebaseAuth, "custom_token").subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValueCount(1);
  }

  @Test public void testSignInWithCustomToken_NotSuccessful() {
    mockNotSuccessfulAuthResult(new IllegalStateException());

    when(mockFirebaseAuth.signInWithCustomToken("custom_token")).thenReturn(mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.signInWithCustomToken(mockFirebaseAuth, "custom_token").subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testSignInWithEmailAndPassword() {
    mockSuccessfulAuthResult();

    when(mockFirebaseAuth.signInWithEmailAndPassword("email", "password")).thenReturn(
        mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.signInWithEmailAndPassword(mockFirebaseAuth, "email", "password").subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValueCount(1);
  }

  @Test public void testSignInWithEmailAndPassword_NotSuccessful() {
    mockNotSuccessfulAuthResult(new IllegalStateException());

    when(mockFirebaseAuth.signInWithEmailAndPassword("email", "password")).thenReturn(
        mockAuthResultTask);

    TestObserver<FirebaseUser> obs = TestObserver.create();

    RxFirebaseAuth.signInWithEmailAndPassword(mockFirebaseAuth, "email", "password").subscribe(obs);

    callOnComplete(mockAuthResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockAuthResultTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testSignOut() {
    TestObserver obs = TestObserver.create();

    RxFirebaseAuth.signOut(mockFirebaseAuth).subscribe(obs);

    verify(mockFirebaseAuth).signOut();

    obs.dispose();

    obs.assertNoErrors();
    obs.assertComplete();
  }

  @Test public void testApplyActionCode() {
    TestObserver obs = TestObserver.create();
    mockSuccessfulResultForTask(mockVoidTask);

    when(mockFirebaseAuth.applyActionCode("code")).thenReturn(mockVoidTask);
    RxFirebaseAuth.applyActionCode(mockFirebaseAuth, "code").subscribe(obs);

    verify(mockFirebaseAuth).applyActionCode("code");

    callOnComplete(mockVoidTask);

    obs.dispose();

    obs.assertNoErrors();
    obs.assertComplete();
  }

  @Test public void testApplyActionCode_NotSuccessful() {
    TestObserver obs = TestObserver.create();
    mockNotSuccessfulResultForTask(mockVoidTask, new IllegalStateException());

    when(mockFirebaseAuth.applyActionCode("code")).thenReturn(mockVoidTask);
    RxFirebaseAuth.applyActionCode(mockFirebaseAuth, "code").subscribe(obs);

    verify(mockFirebaseAuth).applyActionCode("code");

    callOnComplete(mockVoidTask);

    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockVoidTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testCheckActionCode() {
    when(mockActionCodeResult.getOperation()).thenReturn(ActionCodeResult.VERIFY_EMAIL);
    when(mockActionCodeResult.getData(ActionCodeResult.EMAIL)).thenReturn("user@example.com");

    mockSuccessfulResultForTask(mockActionCodeResultTask, mockActionCodeResult);

    when(mockFirebaseAuth.checkActionCode("code")).thenReturn(mockActionCodeResultTask);

    TestObserver<ActionCodeResult> obs = TestObserver.create();

    RxFirebaseAuth.checkActionCode(mockFirebaseAuth, "code").subscribe(obs);

    callOnComplete(mockActionCodeResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockActionCodeResultTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValueCount(1);
    obs.assertValue(new Predicate<ActionCodeResult>() {
      @Override public boolean test(ActionCodeResult result) throws Exception {
        return result.getOperation() == ActionCodeResult.VERIFY_EMAIL && "user@example.com".equals(
            result.getData(ActionCodeResult.EMAIL));
      }
    });
  }

  @Test public void testCheckActionCode_NotSuccessful() {
    mockNotSuccessfulResultForTask(mockActionCodeResultTask, new IllegalStateException());

    when(mockFirebaseAuth.checkActionCode("code")).thenReturn(mockActionCodeResultTask);

    TestObserver<ActionCodeResult> obs = TestObserver.create();

    RxFirebaseAuth.checkActionCode(mockFirebaseAuth, "code").subscribe(obs);

    callOnComplete(mockActionCodeResultTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockActionCodeResultTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testConfirmPasswordReset() {
    TestObserver obs = TestObserver.create();
    mockSuccessfulResultForTask(mockVoidTask);

    when(mockFirebaseAuth.confirmPasswordReset("code", "password")).thenReturn(mockVoidTask);
    RxFirebaseAuth.confirmPasswordReset(mockFirebaseAuth, "code", "password").subscribe(obs);

    verify(mockFirebaseAuth).confirmPasswordReset("code", "password");

    callOnComplete(mockVoidTask);

    obs.dispose();

    obs.assertNoErrors();
    obs.assertComplete();
  }

  @Test public void testConfirmPasswordReset_NotSuccessful() {
    TestObserver obs = TestObserver.create();
    mockNotSuccessfulResultForTask(mockVoidTask, new IllegalStateException());

    when(mockFirebaseAuth.confirmPasswordReset("code", "password")).thenReturn(mockVoidTask);
    RxFirebaseAuth.confirmPasswordReset(mockFirebaseAuth, "code", "password").subscribe(obs);

    verify(mockFirebaseAuth).confirmPasswordReset("code", "password");

    callOnComplete(mockVoidTask);

    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockVoidTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testVerifyPasswordResetCode() {

    mockSuccessfulResultForTask(mockStringTask, "user@example.com");

    when(mockFirebaseAuth.verifyPasswordResetCode("code")).thenReturn(mockStringTask);

    TestObserver<String> obs = TestObserver.create();

    RxFirebaseAuth.verifyPasswordResetCode(mockFirebaseAuth, "code").subscribe(obs);

    callOnComplete(mockStringTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockStringTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValueCount(1);
    obs.assertValue(new Predicate<String>() {
      @Override public boolean test(String result) throws Exception {
        return "user@example.com".equals(result);
      }
    });
  }

  @Test public void testVerifyPasswordResetCode_NotSuccessful() {
    mockNotSuccessfulResultForTask(mockStringTask, new IllegalStateException());

    when(mockFirebaseAuth.verifyPasswordResetCode("code")).thenReturn(mockStringTask);

    TestObserver<String> obs = TestObserver.create();

    RxFirebaseAuth.verifyPasswordResetCode(mockFirebaseAuth, "code").subscribe(obs);

    callOnComplete(mockStringTask);
    obs.dispose();

    // Ensure no more values are emitted after unsubscribe
    callOnComplete(mockStringTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  private <T> void mockSuccessfulResultForTask(Task<T> task, T result) {
    when(task.getResult()).thenReturn(result);
    mockSuccessfulResultForTask(task);
  }

  private void mockSuccessfulResultForTask(Task task) {
    when(task.isSuccessful()).thenReturn(true);
    //noinspection unchecked
    when(task.addOnCompleteListener(onComplete.capture())).thenReturn(task);
  }

  private void mockNotSuccessfulResultForTask(Task task, Exception exception) {
    when(task.isSuccessful()).thenReturn(false);

    when(task.getResult()).thenReturn(null);

    when(task.getException()).thenReturn(exception);
  }

  private void mockSuccessfulAuthResult() {
    when(mockAuthResult.getUser()).thenReturn(mockFirebaseUser);
    mockSuccessfulResultForTask(mockAuthResultTask, mockAuthResult);
  }

  private void mockNotSuccessfulAuthResult(Exception exception) {
    mockNotSuccessfulResultForTask(mockAuthResultTask, exception);
    //noinspection unchecked
  }

  private void mockSuccessfulSendPasswordResetEmailResult() {
    mockSuccessfulResultForTask(mockSendPasswordResetEmailTask);
  }

  private void mockNotSuccessfulSendPasswordResetEmailResult(Exception exception) {
    mockNotSuccessfulResultForTask(mockSendPasswordResetEmailTask, exception);
  }

  private void mockNotSuccessfulFetchProvidersResult(Exception exception) {
    mockNotSuccessfulResultForTask(mockFetchProvidersTask, exception);
  }

  @SuppressWarnings("unchecked") private void callOnComplete(Task<?> task) {
    verify(task).addOnCompleteListener(onComplete.capture());
    onComplete.getValue().onComplete(task);
  }

  private void callOnAuthStateChanged() {
    verify(mockFirebaseAuth).addAuthStateListener(authStateChange.capture());
    authStateChange.getValue().onAuthStateChanged(mockFirebaseAuth);
  }
}
