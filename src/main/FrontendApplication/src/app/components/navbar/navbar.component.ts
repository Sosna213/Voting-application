import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {LocalStorageService} from "../../services/LocalStorage/local-storage.service";
import {AuthService} from "../../services/Auth/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(
    private localStorageService: LocalStorageService,
    private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
  }
  goToLoginPage(){
    this.router.navigate(['login']);
  }
  goToHomePage(){
    this.router.navigate(['']);
  }

  logout(){
    this.authService.logout();
    this.goToHomePage();
  }
  goToRegisterPage(){
    this.router.navigate(['register']);
  }
  isLoggedIn(): boolean{
    if(this.localStorageService.getItem('token') === null){
      return false;
    } else{
      return true;
    }
  }

}
