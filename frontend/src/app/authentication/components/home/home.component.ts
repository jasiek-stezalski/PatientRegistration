import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {User} from '../../../models/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit {
  currentUser: User;
  login: String;
  url: String;

  constructor(public authService: AuthService, public router: Router) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (localStorage.getItem('currentUser')) {
      this.url = 'home';
      this.login = 'Wyloguj';
    } else {
      this.url = 'login';
      this.login = 'Zaloguj';
    }


  }

  ngOnInit() {
  }

// login out from the app
  logInOut() {
    this.authService.logOut()
      .subscribe(
        () => {
          this.router.navigate(['/home']);
        },
        () => {

        });


  }

}
