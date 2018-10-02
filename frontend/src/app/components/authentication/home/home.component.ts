import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';

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

  constructor(public userService: UserService, public router: Router) {
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    if (sessionStorage.getItem('currentUser')) {
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
    this.userService.logOut()
      .subscribe(
        () => {
          this.router.navigate(['/home']);
        },
        () => {

        });


  }

}
