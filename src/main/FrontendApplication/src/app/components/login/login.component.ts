import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";
import {LocalStorageService} from "../../services/local-storage/local-storage.service";

export type UserLoginModel = {
  username: string,
  password: string
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm = this.formBuilder.group(
    {
      username: [null, Validators.required],
      password: [null, Validators.required]
    }
  )
  hide = true;
  readonly loginUrl = '/login';
  response: any;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private localStorage: LocalStorageService) {
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const userLoginData: UserLoginModel = {
       username: this.loginForm.controls['username'].value,
       password: this.loginForm.controls['password'].value
      }
      this.authService.loginUser(this.loginUrl, userLoginData)
        .subscribe( res=>{
        this.localStorage.setItem('token', res.accessToken);
        this.localStorage.setItem('refreshToken', res.refreshToken);
        this.router.navigate(['/users']);
      },
          error => {
          console.error(error);
          });
    } else {
      alert('Dane nieprawidłowe');
    }
  }
  private setToken(key: string, token: string): void {
    this.localStorage.setItem(key, token);
  }

}
