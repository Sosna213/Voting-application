import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserLoginModel} from "../../components/login/login.component";
import {LocalStorageService} from "../LocalStorage/local-storage.service";
import {Observable, tap} from "rxjs";

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
    private localStorageService: LocalStorageService) { }

  public loginUser(url: string, userLoginData: UserLoginModel) {

    const body = new HttpParams()
      .set('username', userLoginData.username)
      .set('password', userLoginData.password)

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/x-www-form-urlencoded');

   this.http.post<LoginResponse>('/login', body.toString(), {
     headers,
     withCredentials: true
   })
     .subscribe(
       res => {
         this.setToken('token', res.accessToken);
         this.setToken('refreshToken', res.refreshToken);
       }
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
