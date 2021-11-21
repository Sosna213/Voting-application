import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../services/User/user.service";
import {Router} from "@angular/router";

export type UserRegisterModel = {
  username: string;
  email: string;
  password: string;
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  registerForm = this.formBuilder.group({
    username: [null, Validators.required],
    email: [null, [Validators.required, Validators.email]],
    password: [null, Validators.required],
    repeatPassword:[null, Validators.required]
  });
  hide = true;
  response: any;
  readonly registerUrl = '/register';

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) { }
  ngOnInit(): void {
  }

  onSubmit(): void {
    if(this.registerForm.valid){
      const data :UserRegisterModel = {
         username: this.registerForm.controls['username'].value,
         email: this.registerForm.controls['email'].value,
         password: this.registerForm.controls['password'].value
      }
      this.response = this.userService.registerUser(this.registerUrl, data);
      this.router.navigate(['login']);
    }
    else {
      alert('Error');
    }
  }

}
