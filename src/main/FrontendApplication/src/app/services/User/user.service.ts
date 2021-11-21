import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserRegisterModel} from "../../components/register/register.component";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public registerUser(url: String, data: UserRegisterModel): Observable<Object>{
    let response: any;
    this.http.post('/register', data).subscribe(response1 =>{
      response = response1;
    });
    return response;
  }
}
