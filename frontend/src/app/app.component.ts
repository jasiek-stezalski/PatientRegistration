import {Component, OnInit} from '@angular/core';
import {User} from './models/user.model';
import {UserService} from './services/user.service';
import {Router} from '@angular/router';
import {Button} from "./resources/button.model";

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
  userPanel: Button[] = [];
  calendar: String;
  modelCalendar: String;

  constructor(public userService: UserService, public router: Router) {
    document.title = 'Rejestracja pancjentów';
  }

  ngOnInit() {
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    if (sessionStorage.getItem('currentUser')) {
      window.document.getElementById('panel').hidden = false;
      if (this.currentUser.role === 'DOCTOR') {
        this.buttons.push({name: 'Tworzenie modelu wizyty', link: '/modelCalendar'});
        this.buttons.push({name: 'Kalendarz', link: '/doctorCalendar'});
        this.userPanel.push({name: 'Panel lekarza', link: '/doctorPanel'});
        this.userPanel.push({name: 'Lista pacjentów', link: '/usersList'});
      }
      else {
        this.buttons.push({name: 'Umów mnie na wizytę', link: '/searchVisits'});
        this.buttons.push({name: 'Kalendarz (do wyrzucenia)', link: '/patientCalendar'});
        this.userPanel.push({name: 'Aktualne wizyty', link: '/userVisits'});
        this.userPanel.push({name: 'Historia wizyt', link: '/userHistory'});
      }

      this.url = '';
      this.login = 'Wyloguj';

    } else {

      this.buttons.push({name: 'Kalendarz', link: '/patientCalendar'});
      this.url = 'login';
      this.login = 'Zaloguj';
    }

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
