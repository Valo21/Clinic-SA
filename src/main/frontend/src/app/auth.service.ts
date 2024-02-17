import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { BehaviorSubject, lastValueFrom, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from './model/User';

const headers = new HttpHeaders().set('Accept', 'application/json');

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  $authenticationState = new BehaviorSubject<AuthStateType>(<AuthStateType>'PENDING');
  authenticationState = this.$authenticationState.asObservable();

  $user = new BehaviorSubject<User | null>(null);
  user = this.$user.asObservable();
  constructor(private http: HttpClient, private location: Location) {}

    isAuthenticated(){
      return this.authenticationState.subscribe(value => value)
    }

  getUser(): Observable<User> {
    return this.http.get<User>('/api/v1/auth', {headers}, )
      .pipe(map((response: User) => {
          if (response !== null) {
              this.$authenticationState.next(<AuthStateType>'AUTHORIZED');
          } else {
              this.$authenticationState.next(<AuthStateType>'NOT_AUTHORIZED');
          }
          return response;
        })
      )
  }

  signInWithGoogle(): void {
      location.href = `${location.origin}/oauth2/authorization/google`;
  }

  logout(): void {
    this.http.post('/api/v1/auth/logout', {}, { withCredentials: true }).subscribe((response: any) => {
      location.href = response.logoutUrl;
    });
  }
}
