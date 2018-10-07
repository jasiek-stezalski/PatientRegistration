import {Component, OnInit} from '@angular/core';
import {User} from './models/user.model';
import {UserService} from './services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  currentUser: User;
  login: String;
  url: String;

  constructor(public userService: UserService, public router: Router) {
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    if (sessionStorage.getItem('currentUser')) {
      this.url = '';
      this.login = 'Wyloguj';
    } else {
      this.url = 'login';
      this.login = 'Zaloguj';
    }


  }

  ngOnInit() {
  }

  logInOut() {
    this.userService.logOut()
      .subscribe(
        () => {
          this.router.navigate(['/']);
        });

  }
}
