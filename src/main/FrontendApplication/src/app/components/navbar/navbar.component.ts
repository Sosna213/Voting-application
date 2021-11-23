import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {LocalStorageService} from "../../services/local-storage/local-storage.service";
import {AuthService} from "../../services/auth/auth.service";

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
  goToUsersPage(){
    this.router.navigate(['users']);
  }
  goToRegisterPage(){
    this.router.navigate(['register']);
  }
  isLoggedIn(): boolean{
      return this.localStorageService.isLoggedIn();
  }

}
