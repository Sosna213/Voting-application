import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserLoginModel} from "../../components/login/login.component";
import {LocalStorageService} from "../local-storage/local-storage.service";
import {catchError, Observable, tap, throwError} from "rxjs";

interface LoginResponse {
  accessToken: string;
  refreshToken: string;
}


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private localStorageService: LocalStorageService) {}

  public loginUser(url: string, userLoginData: UserLoginModel) :Observable<LoginResponse>{

    const body = new HttpParams()
      .set('username', userLoginData.username)
      .set('password', userLoginData.password)

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/x-www-form-urlencoded');

   return this.http.post<LoginResponse>('/login', body.toString(), {
     headers,
     withCredentials: true
   })
     .pipe(
       catchError((err => {
         console.log('Error in authService')
         console.error(err);
         return throwError(err);
       }))
     );
  }

  refreshToken(): Observable<{accessToken: string; refreshToken: string}> {
    const refreshToken = this.localStorageService.getItem('refreshToken');

    return this.http.get<{accessToken: string; refreshToken: string}>(
      '/token-refresh',
      ).pipe(
      tap(response => {
        this.setToken('token', response.accessToken);
        this.setToken('refreshToken', response.refreshToken);
      })
    );
  }
  logout(): void {
    this.localStorageService.removeItem('token');
    this.localStorageService.removeItem('refreshToken');
  }

  private setToken(key: string, token: string): void {
    this.localStorageService.setItem(key, token);
  }
}
