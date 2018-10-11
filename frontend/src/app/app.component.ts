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
  buttons: Button[] = [];
  calendar: String;
  modelCalendar: String;

  constructor(public userService: UserService, public router: Router) {
    document.title = 'Rejestracja pancjentów';
  }

  ngOnInit() {
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    if (sessionStorage.getItem('currentUser')) {
      if (this.currentUser.role === 'DOCTOR') {
        this.buttons.push({name: 'Tworzenie modelu kalendarza', link: '/modelCalendar'});
        this.buttons.push({name: 'Kalendarz', link: '/doctorCalendar'});
      }
      else this.buttons.push({name: 'Kalendarz', link: '/patientCalendar'});

      this.url = '';
      this.login = 'Wyloguj';

    } else {

      this.buttons.push({name: 'Kalendarz', link: '/patientCalendar'});
      this.url = 'login';
      this.login = 'Zaloguj';
    }

    this.buttons.push({name: 'Panel użytkownika', link: '/userPanel'});

    if (sessionStorage.getItem('currentUser')) {
      if (this.currentUser.role === 'DOCTOR') {
        this.calendar = '/doctorCalendar';
        this.modelCalendar = '/modelCalendar';
      }


    } else {
      this.calendar = '/patientCalendar';

    }
  }

  logInOut() {
    this.userService.logOut()
      .subscribe(
        () => {
          this.router.navigate(['/']);
        });

  }
}

export class Button {
  name: String;
  link: String;
}
