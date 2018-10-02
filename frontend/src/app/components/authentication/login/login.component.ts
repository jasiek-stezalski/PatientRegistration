import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  user: User = new User();
  errorMessage: string;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit() {
  }

  login() {
    this.userService.logIn(this.user)
      .subscribe(() => {
          this.router.navigate(['/profile']);
        }, () => {
          this.errorMessage = 'Błąd : Login lub hasło są nieprawidłowe';
        }
      );
  }
}
