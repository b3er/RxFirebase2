@file:Suppress("NOTHING_TO_INLINE")

package org.b3er.rxfirebase.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.memoizrlabs.retrooptional.Optional
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

inline fun FirebaseAuth.authStateChanges()
    : Observable<FirebaseAuth>
    = RxFirebaseAuth.authStateChanges(this)

inline fun FirebaseAuth.rxCreateUserWithEmailAndPassword(email: String, password: String)
    : Single<FirebaseUser>
    = RxFirebaseAuth.createUserWithEmailAndPassword(this, email, password)

inline fun FirebaseAuth.rxFetchProvidersForEmail(email: String)
    : Single<List<String>>
    = RxFirebaseAuth.fetchProvidersForEmail(this, email)
    .map { if (it.isPresent) it.get() else emptyList() }

inline fun FirebaseAuth.rxGetCurrentUser()
    : Single<Optional<FirebaseUser>>
    = RxFirebaseAuth.getCurrentUser(this)

inline fun FirebaseAuth.rxSendPasswordResetEmail(email: String)
    : Completable
    = RxFirebaseAuth.sendPasswordResetEmail(this, email)

inline fun FirebaseAuth.rxSignInAnonymous()
    : Single<FirebaseUser>
    = RxFirebaseAuth.signInAnonymously(this)

inline fun FirebaseAuth.rxSignInWithCredential(credential: AuthCredential)
    : Single<FirebaseUser>
    = RxFirebaseAuth.signInWithCredential(this, credential)

inline fun FirebaseAuth.rxSignInWithCustomToken(token: String)
    : Single<FirebaseUser>
    = RxFirebaseAuth.signInWithCustomToken(this, token)

inline fun FirebaseAuth.rxSignInWithEmailAndPassword(email: String, password: String)
    : Single<FirebaseUser>
    = RxFirebaseAuth.signInWithEmailAndPassword(this, email, password)

inline fun FirebaseAuth.rxSignOut()
    : Completable
    = RxFirebaseAuth.signOut(this)